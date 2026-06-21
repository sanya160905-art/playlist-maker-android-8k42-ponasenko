package com.practicum.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.myapplication.data.database.dao.PlaylistDao
import com.practicum.myapplication.data.database.dao.TracksDao
import com.practicum.myapplication.data.database.entity.PlaylistEntity
import com.practicum.myapplication.data.database.entity.TrackEntity

@Database(
    entities = [TrackEntity::class, PlaylistEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tracksDao(): TracksDao
    abstract fun playlistDao(): PlaylistDao
}