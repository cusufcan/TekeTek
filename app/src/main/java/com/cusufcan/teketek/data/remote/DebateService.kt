package com.cusufcan.teketek.data.remote

import com.cusufcan.teketek.data.model.debate_end.DebateEndResponse
import com.cusufcan.teketek.data.model.debate_next.DebateNextRequest
import com.cusufcan.teketek.data.model.debate_next.DebateNextResponse
import com.cusufcan.teketek.data.model.debate_start.DebateStartRequest
import com.cusufcan.teketek.data.model.debate_start.DebateStartResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DebateService {
    @POST("debate/start")
    suspend fun startDebate(@Body request: DebateStartRequest): DebateStartResponse

    @POST("debate/next")
    suspend fun nextDebate(@Body request: DebateNextRequest): DebateNextResponse

    @POST("debate/end")
    suspend fun endDebate(@Body request: Map<String, String>): DebateEndResponse
}