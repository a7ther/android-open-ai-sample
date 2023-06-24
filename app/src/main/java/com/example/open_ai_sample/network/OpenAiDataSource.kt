package com.example.open_ai_sample.network

import com.example.open_ai_sample.network.model.chatcompletions.ChatCompletions

interface OpenAiDataSource {

    suspend fun chatCompletions(request: ChatCompletions.Request): ChatCompletions.Response
}