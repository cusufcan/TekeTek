package com.cusufcan.teketek.data.model.debate_end

data class DebateEndResponse(
    val summary: String? = null,
    val strengths: List<String>? = null,
    val weaknesses: List<String>? = null,
    val message: String? = null,
)