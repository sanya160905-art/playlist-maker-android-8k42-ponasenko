package com.practicum.myapplication.data.network

import com.practicum.myapplication.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}