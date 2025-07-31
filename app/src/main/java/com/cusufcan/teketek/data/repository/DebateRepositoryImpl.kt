package com.cusufcan.teketek.data.repository

import com.cusufcan.teketek.data.remote.TopicService
import com.cusufcan.teketek.domain.model.Topic
import com.cusufcan.teketek.domain.repository.DebateRepository
import javax.inject.Inject

class DebateRepositoryImpl @Inject constructor(
    private val topicService: TopicService,
) : DebateRepository {
    override suspend fun getAvailableTopics(): List<Topic> {
        return topicService.getTopics()
    }
}