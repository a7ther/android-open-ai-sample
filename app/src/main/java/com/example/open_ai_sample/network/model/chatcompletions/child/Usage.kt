package com.example.open_ai_sample.network.model.chatcompletions.child

import kotlinx.serialization.Serializable

@Serializable
data class Usage(
    val promptTokens: Int? = null,
    val completionTokens: Int? = null,
    val totalTokens: Int? = null,
)
