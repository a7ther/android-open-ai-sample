package com.example.open_ai_sample.network.model.chatcompletions

import com.example.open_ai_sample.network.model.chatcompletions.child.ChatChoice
import com.example.open_ai_sample.network.model.chatcompletions.child.ChatCompletionFunction
import com.example.open_ai_sample.network.model.chatcompletions.child.ChatMessage
import com.example.open_ai_sample.network.model.chatcompletions.child.Usage
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

sealed interface ChatCompletions {
    @Serializable
    data class Request(
        val model: String,
        val messages: List<ChatMessage>,
        val temperature: Double? = null,
        @Json(name = "top_p")
        val topP: Double? = null,
        val n: Int? = null,
        val stop: List<String>? = null,
        @Json(name = "max_tokens")
        val maxTokens: Int? = null,
        @Json(name = "frequency_penalty")
        val presencePenalty: Double? = null,
        @Json(name = "logit_bias")
        val logitBias: Map<String, Int>? = null,
        val user: String? = null,
        val functions: List<ChatCompletionFunction>? = null,
        //TODO: create json decode data class
        @Json(name = "function_call")
        val functionCall: String? = null,
    ) : ChatCompletions

    sealed interface Response : ChatCompletions {
        @Serializable
        data class Success(
            val id: String,
            val created: Int,
            val model: String,
            val choices: List<ChatChoice>,
            val usage: Usage? = null,
        ) : Response

        data class Failure(
            val exception: Exception
        ) : Response
    }
}