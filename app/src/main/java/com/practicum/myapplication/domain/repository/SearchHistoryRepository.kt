package com.practicum.myapplication.domain.repository

import com.practicum.myapplication.model.Track

interface SearchHistoryRepository {
    suspend fun getHistory(): List<Track>
    suspend fun addToHistory(track: Track)
    suspend fun clearHistory()
}