package com.practicum.myapplication.data.repository

import com.practicum.myapplication.creator.DatabaseMock
import com.practicum.myapplication.domain.repository.SearchHistoryRepository
import com.practicum.myapplication.model.Track

class SearchHistoryRepositoryImpl(
    private val database: DatabaseMock
) : SearchHistoryRepository {

    override fun getHistory(): List<Track> {
        return database.getSearchHistory()
    }

    override fun addToHistory(track: Track) {
        database.addToSearchHistory(track)
    }

    override fun clearHistory() {
        database.clearSearchHistory()
    }
}