package com.practicum.myapplication.creator

import com.practicum.myapplication.data.network.RetrofitNetworkClient
import com.practicum.myapplication.data.repository.TracksRepositoryImpl
import com.practicum.myapplication.data.repository.SearchHistoryRepositoryImpl  // ← добавь
import com.practicum.myapplication.domain.repository.TracksRepository
import com.practicum.myapplication.domain.repository.SearchHistoryRepository    // ← добавь

object Creator {
    private val storage = Storage()
    private val database = DatabaseMock()

    private fun getNetworkClient() = RetrofitNetworkClient(storage)

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(getNetworkClient())
    }

    fun getDatabase(): DatabaseMock = database

    fun getSearchHistoryRepository(): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(database)
    }
}