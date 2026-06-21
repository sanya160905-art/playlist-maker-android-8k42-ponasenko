package com.practicum.myapplication.domain.repository

import com.practicum.myapplication.model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getAllPlaylists(): Flow<List<Playlist>>
    fun getPlaylist(playlistId: Long): Flow<Playlist?>
    suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String? = null)
}