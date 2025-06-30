package com.example.docapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.docapp.ui.theme.BluePrimary
import com.example.docapp.ui.theme.poppinsFontFamily

@Composable
fun CovidScreen(navController: NavController) {
    val protocols = listOf(
        "😷 Wear a face mask in public",
        "🧼 Wash your hands regularly",
        "↔️ Maintain 6 ft social distance",
        "🚫 Avoid crowded places",
        "🏠 Stay home if you're unwell",
        "💉 Get vaccinated and boosted"
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // 🔙 Back Arrow
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = BluePrimary,
                modifier = Modifier
                    .padding(4.dp)
                    .size(28.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            // ✅ Centered Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "COVID-19 Safety Protocols",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary,
                    fontFamily = poppinsFontFamily
                )

                Spacer(modifier = Modifier.height(24.dp))

                protocols.forEach { protocol ->
                    Text(
                        text = "• $protocol",
                        fontSize = 16.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}
