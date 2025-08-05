package com.cusufcan.teketek.domain.repository

import com.cusufcan.teketek.data.model.DebateRequest
import com.cusufcan.teketek.data.model.DebateResponse

interface DebateRepository {
    suspend fun getCounterArgument(request: DebateRequest): DebateResponse
}