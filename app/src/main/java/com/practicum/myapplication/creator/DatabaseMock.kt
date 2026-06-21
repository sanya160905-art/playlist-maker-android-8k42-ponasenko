package com.practicum.myapplication.creator

import com.practicum.myapplication.model.Playlist
import com.practicum.myapplication.model.Track

class DatabaseMock {

    private val searchHistory = mutableListOf<Track>()

    private val playlists = mutableListOf(
        Playlist(
            id = 1L,
            name = "Любимый рок",
            description = "Подборка лучших рок-композиций",
            tracks = listOf(
                Track("Группа крови", "Кино", "04:43"),
                Track("Звезда по имени Солнце", "Кино", "03:45")
            )
        ),
        Playlist(
            id = 2L,
            name = "Для настроения",
            description = "Песни на разные случаи жизни",
            tracks = listOf(
                Track("На заре", "Альянс", "03:50")
            )
        )
    )

    fun getSearchHistory(): List<Track> {
        return searchHistory.toList()
    }

    fun addToSearchHistory(track: Track) {
        searchHistory.removeIf { it.trackName == track.trackName && it.artistName == track.artistName }
        searchHistory.add(0, track)
        if (searchHistory.size > 5) {
            searchHistory.removeAt(searchHistory.size - 1)
        }
    }

    fun clearSearchHistory() {
        searchHistory.clear()
    }

    fun getAllPlaylists(): List<Playlist> {
        return playlists.toList()
    }

    fun getPlaylistById(id: Long): Playlist? {
        return playlists.find { it.id == id }
    }

    fun addPlaylist(playlist: Playlist) {
        playlists.add(playlist)
    }
}