package com.practicum.myapplication.data.repository

import com.practicum.myapplication.data.preferences.SearchHistoryPreferences
import com.practicum.myapplication.domain.repository.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences
) : SearchHistoryRepository {

    override suspend fun getHistory(): List<String> {
        return preferences.getEntries()
    }

    override suspend fun addToHistory(query: String) {
        preferences.addEntry(query)
    }

    override suspend fun clearHistory() {
        preferences.clearHistory()
    }
}