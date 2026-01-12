package com.practicum.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.myapplication.creator.Creator
import com.practicum.myapplication.domain.repository.TracksRepository
import com.practicum.myapplication.model.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val tracksRepository: TracksRepository,
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState: StateFlow<SearchState> = _searchScreenState.asStateFlow()

    fun searchTracks(query: String) {
        if (query.isEmpty()) {
            _searchScreenState.update { SearchState.Initial }
            return
        }

        viewModelScope.launch {
            try {
                _searchScreenState.update { SearchState.Loading }
                val results = tracksRepository.searchTracks(query)
                _searchScreenState.update { SearchState.Success(foundList = results) }
            } catch (e: Exception) {
                _searchScreenState.update { SearchState.Error(e.message.toString()) }
            }
        }
    }

    fun resetState() {
        _searchScreenState.update { SearchState.Initial }
    }


    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(Creator.getTracksRepository())
            }
        }
    }
}
