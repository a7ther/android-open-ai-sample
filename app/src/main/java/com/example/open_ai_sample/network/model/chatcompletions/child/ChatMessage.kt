package com.example.open_ai_sample.network.model.chatcompletions.child

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val role: String,
    val content: String? = null,
    val name: String? = null,
    @Json(name = "function_call")
    val functionCall: FunctionCall? = null
)