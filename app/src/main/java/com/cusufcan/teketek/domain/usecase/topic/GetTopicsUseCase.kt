package com.cusufcan.teketek.domain.usecase.topic

import com.cusufcan.teketek.domain.model.Topic
import com.cusufcan.teketek.domain.repository.DebateRepository
import javax.inject.Inject

class GetTopicsUseCase @Inject constructor(
    private val repository: DebateRepository,
) {
    suspend operator fun invoke(): List<Topic> {
        return repository.getAvailableTopics()
    }
}