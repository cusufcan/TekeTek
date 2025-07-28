package com.cusufcan.teketek.data.repository

import com.cusufcan.teketek.data.model.DebateRequest
import com.cusufcan.teketek.data.model.DebateResponse
import com.cusufcan.teketek.data.remote.GeminiApi
import com.cusufcan.teketek.domain.repository.GeminiRepository
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(
    private val api: GeminiApi,
) : GeminiRepository {
    override suspend fun getCounterArgument(request: DebateRequest): DebateResponse {
        return api.getCounterArgument(request)
    }
}