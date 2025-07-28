package com.cusufcan.teketek.data.remote

import com.cusufcan.teketek.data.model.DebateRequest
import com.cusufcan.teketek.data.model.DebateResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GeminiApi {
    @POST("api/debate")
    suspend fun getCounterArgument(@Body request: DebateRequest): DebateResponse
}