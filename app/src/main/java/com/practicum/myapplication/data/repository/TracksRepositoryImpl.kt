package com.practicum.myapplication.data.repository

import com.practicum.myapplication.data.database.AppDatabase
import com.practicum.myapplication.data.database.toEntity
import com.practicum.myapplication.data.database.toTrack
import com.practicum.myapplication.data.network.NetworkClient
import com.practicum.myapplication.data.dto.TracksSearchRequest
import com.practicum.myapplication.data.dto.TracksSearchResponse
import com.practicum.myapplication.domain.repository.TracksRepository
import com.practicum.myapplication.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    database: AppDatabase
) : TracksRepository {

    private val dao = database.tracksDao()

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

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return dao.getTrackByNameAndArtist(track.trackName, track.artistName).map { it?.toTrack() }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return dao.getFavoriteTracks().map { list -> list.map { it.toTrack() } }
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        val existing = dao.findTrackByNameAndArtistOnce(track.trackName, track.artistName)
        if (existing != null) {
            dao.setPlaylistId(existing.id, playlistId)
        } else {
            dao.insertTrack(track.toEntity(playlistId = playlistId))
        }
    }

    override suspend fun deleteTrackFromPlaylist(track: Track, playlistId: Long) {
        dao.deleteTrackFromPlaylist(track.trackName, track.artistName, playlistId)
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        val existing = dao.findTrackByNameAndArtistOnce(track.trackName, track.artistName)
        if (existing != null) {
            dao.updateFavoriteStatus(track.trackName, track.artistName, isFavorite)
        } else {
            dao.insertTrack(track.toEntity(isFavorite = isFavorite))
        }
    }
}