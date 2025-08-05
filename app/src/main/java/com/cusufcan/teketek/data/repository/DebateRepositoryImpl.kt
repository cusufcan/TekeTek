package com.cusufcan.teketek.data.repository

import com.cusufcan.teketek.data.model.DebateRequest
import com.cusufcan.teketek.data.model.DebateResponse
import com.cusufcan.teketek.data.remote.DebateService
import com.cusufcan.teketek.domain.repository.DebateRepository
import javax.inject.Inject

class DebateRepositoryImpl @Inject constructor(
    private val api: DebateService,
) : DebateRepository {
    override suspend fun getCounterArgument(request: DebateRequest): DebateResponse {
        return api.getCounterArgument(request)
    }
}