package com.practicum.myapplication.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.myapplication.model.Track
import kotlinx.coroutines.flow.first

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>
) {
    private val gson = Gson()
    private val historyKey = stringPreferencesKey("search_history_key")

    suspend fun addEntry(track: Track) {
        dataStore.edit { preferences ->
            val historyJson = preferences[historyKey].orEmpty()
            val history = parseHistory(historyJson).toMutableList()

            // удаляем дубликат, если был
            history.removeAll { it.trackName == track.trackName && it.artistName == track.artistName }
            // добавляем в начало
            history.add(0, track)

            val limited = if (history.size > MAX_ENTRIES) history.subList(0, MAX_ENTRIES) else history
            preferences[historyKey] = gson.toJson(limited)
        }
    }

    suspend fun getEntries(): List<Track> {
        val historyJson = dataStore.data.first()[historyKey].orEmpty()
        return parseHistory(historyJson)
    }

    suspend fun clearHistory() {
        dataStore.edit { preferences ->
            preferences[historyKey] = gson.toJson(emptyList<Track>())
        }
    }

    private fun parseHistory(json: String): List<Track> {
        if (json.isEmpty()) return emptyList()
        val type = object : TypeToken<List<Track>>() {}.type
        return try {
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    companion object {
        private const val MAX_ENTRIES = 10
    }
}