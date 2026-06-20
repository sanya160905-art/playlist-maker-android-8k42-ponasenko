package com.practicum.myapplication.data.network

import com.practicum.myapplication.data.dto.TracksSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApiService {
    @GET("/search?entity=song")
    suspend fun search(@Query("term") text: String): Response<TracksSearchResponse>
}