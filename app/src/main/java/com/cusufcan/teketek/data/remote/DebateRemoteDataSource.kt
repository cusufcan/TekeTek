package com.cusufcan.teketek.data.remote

import com.cusufcan.teketek.data.model.debate_end.DebateEndResponse
import com.cusufcan.teketek.data.model.debate_next.DebateNextRequest
import com.cusufcan.teketek.data.model.debate_next.DebateNextResponse
import com.cusufcan.teketek.data.model.debate_start.DebateStartRequest
import com.cusufcan.teketek.data.model.debate_start.DebateStartResponse
import javax.inject.Inject

class DebateRemoteDataSource @Inject constructor(
    private val debateService: DebateService,
) {
    suspend fun startDebate(topic: String): DebateStartResponse {
        return debateService.startDebate(DebateStartRequest(topic))
    }

    suspend fun nextDebate(debateId: String, userArgument: String): DebateNextResponse {
        return debateService.nextDebate(DebateNextRequest(debateId, userArgument))
    }

    suspend fun endDebate(debateId: String): DebateEndResponse {
        return debateService.endDebate(mapOf("debateId" to debateId))
    }
}