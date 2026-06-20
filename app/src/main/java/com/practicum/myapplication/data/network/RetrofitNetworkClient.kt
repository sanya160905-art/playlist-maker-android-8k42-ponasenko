package com.practicum.myapplication.data.network

import com.practicum.myapplication.data.dto.BaseResponse
import com.practicum.myapplication.data.dto.TracksSearchRequest
import com.practicum.myapplication.data.dto.TracksSearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesService = retrofit.create(ITunesApiService::class.java)

    override suspend fun doRequest(dto: Any): BaseResponse {
        if (dto !is TracksSearchRequest) {
            return BaseResponse().apply { resultCode = 400 }
        }

        return try {
            val response = iTunesService.search(dto.expression)
            if (response.isSuccessful) {
                val body = response.body()
                TracksSearchResponse(
                    resultCode = response.code(),
                    resultCount = body?.resultCount ?: 0,
                    results = body?.results ?: emptyList()
                )
            } else {
                BaseResponse().apply { resultCode = response.code() }
            }
        } catch (e: Exception) {
            BaseResponse().apply { resultCode = -1 }
        }
    }
}