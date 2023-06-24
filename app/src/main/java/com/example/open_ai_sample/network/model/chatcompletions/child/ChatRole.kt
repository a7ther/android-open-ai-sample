package com.example.open_ai_sample.network.model.chatcompletions.child

enum class ChatRole(val role: String) {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),
    FUNCTION("function"),
    ;
}

