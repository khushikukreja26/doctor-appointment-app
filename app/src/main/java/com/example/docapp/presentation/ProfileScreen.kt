package com.example.docapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.docapp.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(username: String, navController: NavController) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }

    // Firestore fetch
    LaunchedEffect(true) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(username)
            .get()
            .addOnSuccessListener { doc ->
                name = doc.getString("name") ?: ""
                email = doc.getString("email") ?: ""
                contact = doc.getString("contact") ?: ""
            }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = WhiteBackground) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "My Profile",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = BluePrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileField(label = "Name", value = name)
            ProfileField(label = "Email", value = email)
            ProfileField(label = "Contact", value = contact)

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    navController.navigate("login_screen") {
                        popUpTo("main_screen/$username") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log out", color = Color.White, fontFamily = poppinsFontFamily)
            }
        }
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, fontWeight = FontWeight.SemiBold, fontFamily = poppinsFontFamily)
        Text(text = value, fontSize = 16.sp, fontFamily = poppinsFontFamily, color = PurpleGrey)
    }
}
