package com.cusufcan.teketek.domain.model

data class Message(
    val text: String,
    val fromAI: Boolean,
    var animated: Boolean = false,
)