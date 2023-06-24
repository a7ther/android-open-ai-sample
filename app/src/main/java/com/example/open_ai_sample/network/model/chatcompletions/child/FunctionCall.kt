package com.example.open_ai_sample.network.model.chatcompletions.child

import kotlinx.serialization.Serializable

@Serializable
data class FunctionCall(
    val name: String? = null,
    val arguments: String? = null
)
