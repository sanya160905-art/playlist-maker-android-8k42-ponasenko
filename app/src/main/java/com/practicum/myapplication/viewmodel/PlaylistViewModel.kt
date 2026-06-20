package com.practicum.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.myapplication.creator.Creator
import com.practicum.myapplication.domain.repository.PlaylistsRepository
import com.practicum.myapplication.model.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val playlistId: Long
) : ViewModel() {

    val playlist: Flow<Playlist?> = playlistsRepository.getPlaylist(playlistId)

    companion object {
        fun getViewModelFactory(playlistId: Long): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlaylistViewModel(
                    Creator.getPlaylistsRepository(),
                    playlistId
                )
            }
        }
    }
}