package com.practicum.myapplication.data.network

import com.practicum.myapplication.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}
