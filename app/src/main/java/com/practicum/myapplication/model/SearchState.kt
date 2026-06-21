package com.practicum.myapplication.model

sealed class SearchState {
    object Initial : SearchState()
    object Loading : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data class Error(val error: String) : SearchState()
    data class History(val queries: List<String>) : SearchState()
}