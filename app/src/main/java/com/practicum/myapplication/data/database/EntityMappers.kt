package com.practicum.myapplication.data.database

import com.practicum.myapplication.data.database.entity.PlaylistEntity
import com.practicum.myapplication.data.database.entity.TrackEntity
import com.practicum.myapplication.model.Playlist
import com.practicum.myapplication.model.Track

fun TrackEntity.toTrack(): Track {
    return Track(
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        artworkUrl = artworkUrl,
        isFavorite = isFavorite
    )
}

fun Track.toEntity(isFavorite: Boolean = this.isFavorite, playlistId: Long? = null): TrackEntity {
    return TrackEntity(
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        artworkUrl = artworkUrl,
        isFavorite = isFavorite,
        playlistId = playlistId
    )
}

fun PlaylistEntity.toPlaylist(tracks: List<Track> = emptyList()): Playlist {
    return Playlist(
        id = id,
        name = name,
        description = description,
        tracks = tracks
    )
}