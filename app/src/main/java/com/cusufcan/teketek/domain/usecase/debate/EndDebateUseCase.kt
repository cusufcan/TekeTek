package com.cusufcan.teketek.domain.usecase.debate

import com.cusufcan.teketek.data.model.debate_end.DebateEndResponse
import com.cusufcan.teketek.domain.repository.DebateRepository
import javax.inject.Inject

class EndDebateUseCase @Inject constructor(
    private val repository: DebateRepository,
) {
    suspend operator fun invoke(debateId: String): DebateEndResponse {
        return repository.endDebate(debateId)
    }
}