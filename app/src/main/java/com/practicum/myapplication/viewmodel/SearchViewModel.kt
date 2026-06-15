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
        showHistory()
    }

    fun onTrackClicked(track: com.practicum.myapplication.model.Track) {
        historyRepository.addToHistory(track)
    }

    fun clearHistory() {
        historyRepository.clearHistory()
        _searchScreenState.update { SearchState.Initial }
    }

    private fun showHistory() {
        val history = historyRepository.getHistory()
        if (history.isEmpty()) {
            _searchScreenState.update { SearchState.Initial }
        } else {
            _searchScreenState.update { SearchState.History(history) }
        }
    }

    private suspend fun performSearch(query: String) {
        try {
            _searchScreenState.update { SearchState.Loading }
            val results = tracksRepository.searchTracks(query)
            _searchScreenState.update { SearchState.Success(foundList = results) }
        } catch (e: Exception) {
            _searchScreenState.update { SearchState.Error(e.message.toString()) }
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