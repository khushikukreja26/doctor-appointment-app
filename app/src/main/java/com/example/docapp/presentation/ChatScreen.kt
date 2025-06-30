package com.example.docapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.docapp.model.Message
import com.example.docapp.ui.theme.*
import com.example.docapp.viewmodel.FakeChatViewModel

@Composable
fun ChatScreen() {
    val chatViewModel: FakeChatViewModel = viewModel()
    val messages by chatViewModel.messages.collectAsState()
    val isTyping by chatViewModel.isTyping.collectAsState()

    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteBackground)
            .padding(16.dp)
    ) {
        // 🧠 Header
        Text(
            text = "DocChatBot 🤖",
            fontFamily = poppinsFontFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = BluePrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Divider(color = LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(12.dp))

        // 💬 Message List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 8.dp),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                ChatBubble(message)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (isTyping) {
                item {
                    Text(
                        text = "DocChatBot is typing...",
                        color = PurpleGrey,
                        fontSize = 13.sp,
                        fontFamily = poppinsFontFamily,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }

        // ✍ Input Area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBlue, shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                placeholder = {
                    Text("Ask your health question...")
                },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 48.dp),
                maxLines = 4,
                singleLine = false,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = BluePrimary,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            IconButton(
                onClick = {
                    if (input.isNotBlank()) {
                        chatViewModel.sendMessage(input)
                        input = ""
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = BluePrimary
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    val alignment = if (message.isUser) Alignment.End else Alignment.Start
    val bubbleColor = if (message.isUser) BluePrimary else LightGray
    val textColor = if (message.isUser) Color.White else Color.Black

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = bubbleColor,
            tonalElevation = 2.dp,
            modifier = Modifier.widthIn(max = 300.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                color = textColor
            )
        }
    }
}