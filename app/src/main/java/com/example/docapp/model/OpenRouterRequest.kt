package com.example.docapp.model

data class OpenRouterRequest(
    val model: String = "openai/gpt-3.5-turbo" ,  // You can try others too
    val messages: List<OpenRouterMessage> = listOf(
        OpenRouterMessage("system", "You are a helpful AI assistant.")
    )
)

data class OpenRouterMessage(
    val role: String,
    val content: String
)