package com.practicum.myapplication.model

sealed class TrackScreenState {
    object Loading : TrackScreenState()
    data class Content(
        val trackModel: TrackModel,
    ) : TrackScreenState()
}
