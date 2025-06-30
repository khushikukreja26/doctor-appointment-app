package com.example.docapp.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.docapp.ui.theme.BluePrimary
import com.example.docapp.ui.theme.PurpleGrey
import com.example.docapp.ui.theme.WhiteBackground
import com.example.docapp.ui.theme.poppinsFontFamily
import com.example.docapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

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
                text = "Welcome Back 👋",
                fontFamily = poppinsFontFamily,
                fontSize = 24.sp,
                color = BluePrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                label = "Username",
                value = username,
                onValueChange = { username = it }
            )

            CustomTextField(
                label = "Password",
                value = password,
                onValueChange = { password = it },
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    authViewModel.loginUser(
                        username = username,
                        password = password,
                        context = context
                    ) { loggedInUsername ->
                        navController.navigate("main_screen/$loggedInUsername") {
                            popUpTo("login_screen") { inclusive = true }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Login",
                    fontFamily = poppinsFontFamily,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = {
                navController.navigate("register_screen")
            }) {
                Text(
                    text = "Don't have an account? Register",
                    color = PurpleGrey,
                    fontFamily = poppinsFontFamily
                )
            }
        }
    }
}
