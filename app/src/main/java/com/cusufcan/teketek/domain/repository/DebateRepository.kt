package com.cusufcan.teketek.domain.repository

import com.cusufcan.teketek.domain.model.Topic

interface DebateRepository {
    suspend fun getAvailableTopics(): List<Topic>
}