package com.practicum.myapplication.creator

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.practicum.myapplication.data.database.AppDatabase
import com.practicum.myapplication.data.network.RetrofitNetworkClient
import com.practicum.myapplication.data.preferences.SearchHistoryPreferences
import com.practicum.myapplication.data.repository.TracksRepositoryImpl
import com.practicum.myapplication.data.repository.SearchHistoryRepositoryImpl
import com.practicum.myapplication.data.repository.PlaylistsRepositoryImpl
import com.practicum.myapplication.domain.repository.TracksRepository
import com.practicum.myapplication.domain.repository.SearchHistoryRepository
import com.practicum.myapplication.domain.repository.PlaylistsRepository

private val Context.dataStore by preferencesDataStore(name = "search_history_preferences")

object Creator {

    private lateinit var appDatabase: AppDatabase
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context
        appDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "playlist_maker_database"
        ).build()
    }

    private fun getNetworkClient() = RetrofitNetworkClient()

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(getNetworkClient(), appDatabase)
    }

    fun getSearchHistoryRepository(): SearchHistoryRepository {
        val preferences = SearchHistoryPreferences(appContext.dataStore)
        return SearchHistoryRepositoryImpl(preferences)
    }

    fun getPlaylistsRepository(): PlaylistsRepository {
        return PlaylistsRepositoryImpl(appDatabase)
    }
}