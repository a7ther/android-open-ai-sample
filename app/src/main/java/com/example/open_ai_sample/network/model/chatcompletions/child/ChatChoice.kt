package com.example.open_ai_sample.network.model.chatcompletions.child

import kotlinx.serialization.Serializable

@Serializable
data class ChatChoice(
    val index: Int? = null,
    val message: ChatMessage? = null,
    val finishReason: String? = null,
)
