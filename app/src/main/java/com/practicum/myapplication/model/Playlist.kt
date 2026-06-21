package com.practicum.myapplication.model

data class Playlist(
    val id: Long,
    val name: String,
    val description: String,
    val coverImageUri: String? = null,
    val tracks: List<Track> = emptyList()
)