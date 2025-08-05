package com.cusufcan.teketek.domain.repository

import com.cusufcan.teketek.domain.model.Topic

interface TopicRepository {
    suspend fun getAvailableTopics(): List<Topic>
}