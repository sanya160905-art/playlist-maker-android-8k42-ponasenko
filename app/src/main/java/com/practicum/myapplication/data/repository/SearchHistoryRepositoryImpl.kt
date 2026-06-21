package com.practicum.myapplication.data.repository

import com.practicum.myapplication.data.preferences.SearchHistoryPreferences
import com.practicum.myapplication.domain.repository.SearchHistoryRepository
import com.practicum.myapplication.model.Track

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences
) : SearchHistoryRepository {

    override suspend fun getHistory(): List<Track> {
        return preferences.getEntries()
    }

    override suspend fun addToHistory(track: Track) {
        preferences.addEntry(track)
    }

    override suspend fun clearHistory() {
        preferences.clearHistory()
    }
}