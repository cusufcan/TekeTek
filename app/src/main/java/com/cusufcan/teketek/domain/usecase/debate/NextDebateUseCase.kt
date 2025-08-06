package com.cusufcan.teketek.domain.usecase.debate

import com.cusufcan.teketek.data.model.debate_next.DebateNextResponse
import com.cusufcan.teketek.domain.repository.DebateRepository
import javax.inject.Inject

class NextDebateUseCase @Inject constructor(
    private val repository: DebateRepository,
) {
    suspend operator fun invoke(debateId: String, userArgument: String): DebateNextResponse {
        return repository.nextDebate(debateId, userArgument)
    }
}