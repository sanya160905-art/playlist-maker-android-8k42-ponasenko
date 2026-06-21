package com.practicum.myapplication.domain.repository

interface SearchHistoryRepository {
    suspend fun getHistory(): List<String>
    suspend fun addToHistory(query: String)
    suspend fun clearHistory()
}