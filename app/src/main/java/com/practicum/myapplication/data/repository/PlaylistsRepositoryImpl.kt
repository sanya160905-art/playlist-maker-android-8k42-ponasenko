package com.practicum.myapplication.data.repository

import com.practicum.myapplication.creator.DatabaseMock
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import com.practicum.myapplication.model.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PlaylistsRepositoryImpl(
    private val database: DatabaseMock
) : PlaylistsRepository {

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return flowOf(database.getAllPlaylists())
    }

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return flowOf(database.getPlaylistById(playlistId))
    }
}