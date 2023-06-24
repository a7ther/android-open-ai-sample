package com.example.open_ai_sample.network.model.chatcompletions.child

import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletionFunction(
    val name: String? = null,
    val description: String? = null,
    //TODO: create json decode data class
    val parameters: String? = null,
)
