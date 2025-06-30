package com.example.docapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.docapp.ui.theme.BluePrimary
import com.example.docapp.ui.theme.PurpleGrey
import com.example.docapp.ui.theme.WhiteBackground
import com.example.docapp.ui.theme.poppinsFontFamily

@Composable
fun RegisterScreen(navController: NavController, onRegister: (String, String, String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = WhiteBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Account",
                fontFamily = poppinsFontFamily,
                fontSize = 24.sp,
                color = BluePrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(label = "Full Name", value = name, onValueChange = { name = it })
            CustomTextField(label = "Username", value = username, onValueChange = { username = it })
            CustomTextField(label = "Email", value = email, onValueChange = { email = it }, keyboardType = KeyboardType.Email)
            CustomTextField(label = "Contact Number", value = contact, onValueChange = { contact = it }, keyboardType = KeyboardType.Phone)
            CustomTextField(label = "Password", value = password, onValueChange = { password = it }, isPassword = true)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onRegister(name, username, email, contact, password)
                },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Register",
                    fontFamily = poppinsFontFamily,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                navController.navigate("login_screen")
            }) {
                Text(
                    text = "Already have an account? Login",
                    color = PurpleGrey,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontFamily = poppinsFontFamily,
                color = PurpleGrey
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BluePrimary,
            unfocusedBorderColor = PurpleGrey.copy(alpha = 0.5f)
        )
    )
}

