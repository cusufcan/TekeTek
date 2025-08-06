package com.cusufcan.teketek.domain.usecase.debate

import com.cusufcan.teketek.data.model.debate_start.DebateStartResponse
import com.cusufcan.teketek.domain.repository.DebateRepository
import javax.inject.Inject

class StartDebateUseCase @Inject constructor(
    private val repository: DebateRepository,
) {
    suspend operator fun invoke(topic: String): DebateStartResponse {
        return repository.startDebate(topic)
    }
}