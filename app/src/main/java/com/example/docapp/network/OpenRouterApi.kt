package com.example.docapp.network

import com.example.docapp.model.OpenRouterRequest
import com.example.docapp.model.OpenRouterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenRouterApi {
    @Headers(
        "Authorization: Bearer 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",
        "Content-Type: application/json",
        "HTTP-Referer: https://docapp.com",  // optional, change if needed
        "X-Title: DocAppChat"
    )
    @POST("v1/chat/completions")
    suspend fun getChatCompletion(
        @Body request: OpenRouterRequest
    ): Response<OpenRouterResponse>
}