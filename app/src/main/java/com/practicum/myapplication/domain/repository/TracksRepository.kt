package com.practicum.myapplication.domain.repository

import com.practicum.myapplication.model.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}
