package com.practicum.myapplication.creator

import com.practicum.myapplication.data.network.RetrofitNetworkClient
import com.practicum.myapplication.data.repository.TracksRepositoryImpl
import com.practicum.myapplication.data.repository.SearchHistoryRepositoryImpl
import com.practicum.myapplication.data.repository.PlaylistsRepositoryImpl
import com.practicum.myapplication.domain.repository.TracksRepository
import com.practicum.myapplication.domain.repository.SearchHistoryRepository
import com.practicum.myapplication.domain.repository.PlaylistsRepository

object Creator {
    private val database = DatabaseMock()

    private fun getNetworkClient() = RetrofitNetworkClient()

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(getNetworkClient())
    }

    fun getDatabase(): DatabaseMock = database

    fun getSearchHistoryRepository(): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(database)
    }

    fun getPlaylistsRepository(): PlaylistsRepository {
        return PlaylistsRepositoryImpl(database)
    }
}