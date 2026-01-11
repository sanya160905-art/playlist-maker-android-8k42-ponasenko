package com.practicum.myapplication.creator

import com.practicum.myapplication.data.network.RetrofitNetworkClient
import com.practicum.myapplication.data.repository.TracksRepositoryImpl
import com.practicum.myapplication.domain.repository.TracksRepository

object Creator {
    private val storage = Storage()

    private fun getNetworkClient() = RetrofitNetworkClient(storage)

    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(getNetworkClient())
    }
}
