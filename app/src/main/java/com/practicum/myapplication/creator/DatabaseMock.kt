package com.practicum.myapplication.creator

import com.practicum.myapplication.model.Track

class DatabaseMock {

    // История поиска — максимум 5 последних треков
    private val searchHistory = mutableListOf<Track>()

    fun getSearchHistory(): List<Track> {
        return searchHistory.toList()
    }

    fun addToSearchHistory(track: Track) {
        // Если трек уже есть — удаляем его (чтобы переместить наверх)
        searchHistory.removeIf { it.trackName == track.trackName && it.artistName == track.artistName }
        // Добавляем в начало списка
        searchHistory.add(0, track)
        // Оставляем только последние 5
        if (searchHistory.size > 5) {
            searchHistory.removeAt(searchHistory.size - 1)
        }
    }

    fun clearSearchHistory() {
        searchHistory.clear()
    }
}