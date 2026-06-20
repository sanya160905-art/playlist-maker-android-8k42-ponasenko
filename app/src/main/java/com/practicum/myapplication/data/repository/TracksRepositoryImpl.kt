package com.practicum.myapplication.data.repository

import com.practicum.myapplication.data.network.NetworkClient
import com.practicum.myapplication.data.dto.TracksSearchRequest
import com.practicum.myapplication.data.dto.TracksSearchResponse
import com.practicum.myapplication.domain.repository.TracksRepository
import com.practicum.myapplication.model.Track

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override suspend fun searchTracks(expression: String): Result<List<Track>> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))

        return if (response.resultCode == 200) {
            val tracks = (response as TracksSearchResponse).results.mapNotNull { dto ->
                val name = dto.trackName ?: return@mapNotNull null
                val artist = dto.artistName ?: return@mapNotNull null
                val millis = dto.trackTimeMillis ?: 0
                val seconds = millis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds - minutes * 60)
                Track(
                    trackName = name,
                    artistName = artist,
                    trackTime = trackTime,
                    artworkUrl = dto.artworkUrl100
                )
            }
            Result.success(tracks)
        } else {
            Result.failure(Exception("Server error: ${response.resultCode}"))
        }
    }
}