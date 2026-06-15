package com.practicum.myapplication.domain.repository

import com.practicum.myapplication.model.Track

interface SearchHistoryRepository {
    fun getHistory(): List<Track>
    fun addToHistory(track: Track)
    fun clearHistory()
}