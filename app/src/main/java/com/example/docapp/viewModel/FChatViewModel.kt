package com.example.docapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.docapp.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class FakeChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()

    fun sendMessage(input: String) {
        _messages.value += Message(input, true) // user's message

        viewModelScope.launch {
            _isTyping.value = true

            delay(1500) // fake typing delay

            val fakeReply = """
                I'm sorry to hear you're experiencing a headache. 
                Here are a few things you can try:
                • Drink plenty of water 💧
                • Rest in a quiet, dark room 🛏
                • Try a cold or warm compress 🧊
                • If needed, consider an over-the-counter pain reliever 💊

                If it persists, please consult a doctor. 💬
            """.trimIndent()

            _messages.value += Message(fakeReply, false)

            _isTyping.value = false
        }
    }
}