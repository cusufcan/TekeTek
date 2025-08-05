package com.cusufcan.teketek.data.repository

import com.cusufcan.teketek.data.remote.TopicService
import com.cusufcan.teketek.domain.model.Topic
import com.cusufcan.teketek.domain.repository.TopicRepository
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
    private val topicService: TopicService,
) : TopicRepository {
    override suspend fun getAvailableTopics(): List<Topic> {
        return topicService.getTopics()
    }
}