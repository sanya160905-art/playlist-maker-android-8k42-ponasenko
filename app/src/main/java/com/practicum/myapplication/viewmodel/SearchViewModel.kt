package com.practicum.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.myapplication.creator.Creator
import com.practicum.myapplication.domain.repository.SearchHistoryRepository
import com.practicum.myapplication.domain.repository.TracksRepository
import com.practicum.myapplication.model.SearchState
import com.practicum.myapplication.model.Track
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val historyRepository: SearchHistoryRepository,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private var lastQuery: String = ""

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState: StateFlow<SearchState> = _searchScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000L)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isEmpty()) {
                        showHistory()
                    } else {
                        performSearch(query)
                    }
                }
        }
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    fun showHistoryIfExists() {
        viewModelScope.launch {
            showHistory()
        }
    }

    fun onTrackClicked(track: Track) {
        viewModelScope.launch {
            historyRepository.addToHistory(track)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            historyRepository.clearHistory()
            _searchScreenState.update { SearchState.Initial }
        }
    }

    fun retrySearch() {
        if (lastQuery.isNotEmpty()) {
            viewModelScope.launch {
                performSearch(lastQuery)
            }
        }
    }

    private suspend fun showHistory() {
        val history = historyRepository.getHistory()
        if (history.isEmpty()) {
            _searchScreenState.update { SearchState.Initial }
        } else {
            _searchScreenState.update { SearchState.History(history) }
        }
    }

    private suspend fun performSearch(query: String) {
        lastQuery = query
        _searchScreenState.update { SearchState.Loading }
        val result = tracksRepository.searchTracks(query)
        result.onSuccess { tracks ->
            _searchScreenState.update { SearchState.Success(foundList = tracks) }
        }.onFailure { error ->
            _searchScreenState.update { SearchState.Error(error.message ?: "Unknown error") }
        }
    }

    fun resetState() {
        _searchQuery.value = ""
        _searchScreenState.update { SearchState.Initial }
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    Creator.getTracksRepository(),
                    Creator.getSearchHistoryRepository()
                )
            }
        }
    }
}