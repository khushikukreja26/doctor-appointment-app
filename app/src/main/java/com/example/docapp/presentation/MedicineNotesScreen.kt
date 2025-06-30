package com.example.docapp.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.docapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineNotesScreen(navController: NavController) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "Medicine Notes 💊",
                        fontFamily = poppinsFontFamily,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = BluePrimary)
            )
        },
        containerColor = WhiteBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title (e.g., Fever Kit)") },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                placeholder = { Text("Write your prescribed medicines...") },
                label = { Text("Medicine Notes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 10,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    saveMedicineNote(context, title, note)
                    Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show()
                    title = ""
                    note = ""
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Text(text = "Save", color = Color.White, fontFamily = poppinsFontFamily)
            }
        }
    }
}

fun saveMedicineNote(context: Context, title: String, note: String) {
    val sharedPreferences = context.getSharedPreferences("med_notes", Context.MODE_PRIVATE)
    sharedPreferences.edit().apply {
        putString("title", title)
        putString("note", note)
        apply()
    }
}