package com.practicum.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicum.myapplication.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity): Long

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist LIMIT 1")
    suspend fun findTrackByNameAndArtistOnce(name: String, artist: String): TrackEntity?

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist LIMIT 1")
    fun getTrackByNameAndArtist(name: String, artist: String): Flow<TrackEntity?>

    @Query("SELECT * FROM tracks WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks WHERE playlistId = :playlistId")
    fun getTracksByPlaylistId(playlistId: Long): Flow<List<TrackEntity>>

    @Query("UPDATE tracks SET isFavorite = :isFavorite WHERE trackName = :name AND artistName = :artist")
    suspend fun updateFavoriteStatus(name: String, artist: String, isFavorite: Boolean)

    @Query("UPDATE tracks SET playlistId = :playlistId WHERE id = :id")
    suspend fun setPlaylistId(id: Long, playlistId: Long)

    @Query("DELETE FROM tracks WHERE trackName = :name AND artistName = :artist AND playlistId = :playlistId")
    suspend fun deleteTrackFromPlaylist(name: String, artist: String, playlistId: Long)
}