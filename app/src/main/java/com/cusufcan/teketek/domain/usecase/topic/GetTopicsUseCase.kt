package com.cusufcan.teketek.domain.usecase.topic

import com.cusufcan.teketek.domain.model.Topic
import com.cusufcan.teketek.domain.repository.TopicRepository
import javax.inject.Inject

class GetTopicsUseCase @Inject constructor(
    private val repository: TopicRepository,
) {
    suspend operator fun invoke(): List<Topic> {
        return repository.getAvailableTopics()
    }
}