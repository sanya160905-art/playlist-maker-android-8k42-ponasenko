package com.practicum.myapplication.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>
) {
    private val gson = Gson()
    private val historyKey = stringPreferencesKey("search_history_key")

    suspend fun addEntry(query: String) {
        if (query.isBlank()) return
        dataStore.edit { preferences ->
            val historyJson = preferences[historyKey].orEmpty()
            val history = parseHistory(historyJson).toMutableList()

            history.removeAll { it.equals(query, ignoreCase = true) }
            history.add(0, query)

            val limited = if (history.size > MAX_ENTRIES) history.subList(0, MAX_ENTRIES) else history
            preferences[historyKey] = gson.toJson(limited)
        }
    }

    suspend fun getEntries(): List<String> {
        val historyJson = dataStore.data.first()[historyKey].orEmpty()
        return parseHistory(historyJson)
    }

    suspend fun clearHistory() {
        dataStore.edit { preferences ->
            preferences[historyKey] = gson.toJson(emptyList<String>())
        }
    }

    private fun parseHistory(json: String): List<String> {
        if (json.isEmpty()) return emptyList()
        val type = object : TypeToken<List<String>>() {}.type
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