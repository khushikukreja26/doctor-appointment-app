package com.example.docapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docapp.model.*
import com.example.docapp.network.OpenRouterApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()

    private val api: OpenRouterApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://openrouter.ai/api/") // HTTP for emulator
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(OpenRouterApi::class.java)
    }

    fun sendMessage(input: String) {
        val userMsg = Message(input, true)
        _messages.value += userMsg

        viewModelScope.launch {
            _isTyping.value = true
            try {
                val request = OpenRouterRequest(
                    model = "openai/gpt-3.5-turbo",
                    messages = listOf(
                        OpenRouterMessage("system", "You are a helpful assistant."),
                        OpenRouterMessage("user", input)
                    )
                )

                val response = api.getChatCompletion(request)

                Log.d("💬 ChatGPT", "Success: ${response.isSuccessful}")
                Log.d("💬 ChatGPT", "Raw: ${response.raw()}")
                Log.d("💬 ChatGPT", "Body: ${response.body()}")
                Log.d("💬 ChatGPT", "ErrorBody: ${response.errorBody()?.string()}")

                val choices = response.body()?.choices
                if (!choices.isNullOrEmpty()) {
                    val reply = choices.first().message.content
                    Log.d("💬 ChatGPT", "Reply Content: $reply")
                    _messages.value += Message(reply ?: "🤖 No content in reply", false)
                } else {
                    Log.d("💬 ChatGPT", "Choices were empty/null!")
                    _messages.value += Message("⚠ No reply received", false)
                }

            } catch (e: Exception) {
                Log.e("💥 ChatGPT Error", "Exception: ${e.localizedMessage}", e)
                _messages.value += Message("❌ Exception: ${e.localizedMessage}", false)
            } finally {
                _isTyping.value = false
            }
        }
    }
}