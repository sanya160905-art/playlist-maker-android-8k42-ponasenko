package com.practicum.myapplication.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemePreferences(
    private val dataStore: DataStore<Preferences>
) {
    private val darkThemeKey = booleanPreferencesKey("dark_theme_key")

    val isDarkTheme: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[darkThemeKey] ?: false
    }

    suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[darkThemeKey] = enabled
        }
    }
}