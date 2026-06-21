package com.practicum.myapplication.data.repository

import com.practicum.myapplication.data.database.AppDatabase
import com.practicum.myapplication.data.database.entity.PlaylistEntity
import com.practicum.myapplication.data.database.toPlaylist
import com.practicum.myapplication.data.database.toTrack
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import com.practicum.myapplication.model.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val database: AppDatabase
) : PlaylistsRepository {

    private val playlistDao = database.playlistDao()
    private val tracksDao = database.tracksDao()

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylists().map { entities ->
            entities.map { entity ->
                val tracks = tracksDao.getTracksByPlaylistId(entity.id).first().map { it.toTrack() }
                entity.toPlaylist(tracks)
            }
        }
    }

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return combine(
            playlistDao.getPlaylistById(playlistId),
            tracksDao.getTracksByPlaylistId(playlistId)
        ) { playlistEntity, trackEntities ->
            playlistEntity?.toPlaylist(trackEntities.map { it.toTrack() })
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?) {
        playlistDao.insertPlaylist(
            PlaylistEntity(name = name, description = description, coverImageUri = coverImageUri)
        )
    }
}