package com.cusufcan.teketek.data.repository

import com.cusufcan.teketek.data.model.debate_end.DebateEndResponse
import com.cusufcan.teketek.data.model.debate_next.DebateNextResponse
import com.cusufcan.teketek.data.model.debate_start.DebateStartResponse
import com.cusufcan.teketek.data.remote.DebateRemoteDataSource
import com.cusufcan.teketek.domain.repository.DebateRepository
import javax.inject.Inject

class DebateRepositoryImpl @Inject constructor(
    private val debateRemoteDataSource: DebateRemoteDataSource,
) : DebateRepository {
    override suspend fun startDebate(topic: String): DebateStartResponse {
        return debateRemoteDataSource.startDebate((topic))
    }

    override suspend fun nextDebate(debateId: String, userArgument: String): DebateNextResponse {
        return debateRemoteDataSource.nextDebate(debateId, userArgument)
    }

    override suspend fun endDebate(debateId: String): DebateEndResponse {
        return debateRemoteDataSource.endDebate(debateId)
    }
}