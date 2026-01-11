package com.practicum.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.myapplication.model.TrackModel
import com.practicum.myapplication.model.TrackScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrackViewModel(
    private val trackId: String,
) : ViewModel() {

    // StateFlow для состояния экрана
    private val _trackScreenState = MutableStateFlow<TrackScreenState>(TrackScreenState.Loading)
    val trackScreenState: StateFlow<TrackScreenState> = _trackScreenState.asStateFlow()

    init {
        Log.d("TEST", "TrackViewModel init!: $trackId")
        loadTrackData()
    }

    private fun loadTrackData() {
        viewModelScope.launch {
            Log.d("TEST", "Start loading track data...")

            // Имитация загрузки данных трека через Interactor
            delay(2000) // 2 секунды загрузки

            // Создаём mock-данные трека
            val trackModel = TrackModel(
                id = trackId,
                name = "Smells Like Teen Spirit",
                author = "Nirvana",
                pictureUrl = "https://via.placeholder.com/300"
            )

            // Обновляем состояние на Content
            _trackScreenState.value = TrackScreenState.Content(trackModel)
            Log.d("TEST", "Track data loaded: ${trackModel.name}")
        }
    }

    companion object {
        fun getViewModelFactory(trackId: String) = viewModelFactory {
            initializer {
                TrackViewModel(trackId)
            }
        }
    }
}
