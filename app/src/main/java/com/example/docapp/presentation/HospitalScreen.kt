package com.example.docapp.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.docapp.R
import com.example.docapp.ui.theme.*

data class Hospital(
    val name: String,
    val address: String,
    val phone: String
)

@Composable
fun HospitalListScreen(navController: NavController) {
    val context = LocalContext.current

    val hospitals = listOf(
        Hospital("AIIMS Bhopal", "Saket Nagar, Bhopal", "07552497700"),
        Hospital("BMHRC", "Raisen Bypass Road, Bhopal", "07556661100"),
        Hospital("Hamidia Hospital", "Royal Market, Bhopal", "07552740641"),
        Hospital("Kamla Nehru Hospital", "Gandhi Medical College Campus", "07552540305"),
        Hospital("Bansal Hospital", "Shahpura, Bhopal", "07553900000"),
        Hospital("Narmada Hospital", "J.K. Road, Bhopal", "07552422525"),
        Hospital("LBS Hospital", "Gandhi Nagar, Bhopal", "07552580291"),
        Hospital("People’s General Hospital", "People's Campus, Bhanpur", "07552763331"),
        Hospital("Noble Hospital", "Kolar Road, Bhopal", "07552810027"),
        Hospital("Navodaya Hospital", "Lalghati, Bhopal", "07554200002")
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        // ⬅️ Back button
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = BluePrimary
                )
            }
            Text(
                text = "Nearby Hospitals",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(hospitals) { hospital ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = LightBlue),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = hospital.name,
                            fontFamily = poppinsFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = hospital.address,
                            fontFamily = poppinsFontFamily,
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:${hospital.phone}")
                                }
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Call Hospital", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
