package com.cusufcan.teketek.domain.repository

import com.cusufcan.teketek.data.model.debate_end.DebateEndResponse
import com.cusufcan.teketek.data.model.debate_next.DebateNextResponse
import com.cusufcan.teketek.data.model.debate_start.DebateStartResponse

interface DebateRepository {
    suspend fun startDebate(topic: String): DebateStartResponse
    suspend fun nextDebate(debateId: String, userArgument: String): DebateNextResponse
    suspend fun endDebate(debateId: String): DebateEndResponse
}