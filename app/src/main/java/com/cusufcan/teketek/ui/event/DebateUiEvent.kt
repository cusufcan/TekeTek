package com.cusufcan.teketek.ui.event

sealed class DebateUiEvent {
    data class FinishDebate(
        private val debateId: String? = null,
        val summary: String? = null,
        val strengths: List<String>? = null,
        val weaknesses: List<String>? = null,
        val message: String? = null,
    ) : DebateUiEvent()
}