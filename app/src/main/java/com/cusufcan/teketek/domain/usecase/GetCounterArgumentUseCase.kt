package com.cusufcan.teketek.domain.usecase

import com.cusufcan.teketek.data.model.DebateRequest
import com.cusufcan.teketek.data.model.DebateResponse
import com.cusufcan.teketek.domain.repository.DebateRepository
import javax.inject.Inject

class GetCounterArgumentUseCase @Inject constructor(
    private val repository: DebateRepository,
) {
    suspend operator fun invoke(request: DebateRequest): DebateResponse {
        return repository.getCounterArgument(request)
    }
}