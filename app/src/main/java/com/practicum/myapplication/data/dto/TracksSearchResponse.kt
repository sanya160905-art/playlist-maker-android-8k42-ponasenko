package com.practicum.myapplication.data.dto

import com.google.gson.annotations.SerializedName

class TracksSearchResponse(
    resultCode: Int = 0,
    @SerializedName("resultCount") val resultCount: Int = 0,
    @SerializedName("results") val results: List<TrackDto> = emptyList()
) : BaseResponse(resultCode)