package com.example.docapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.docapp.R
import com.example.docapp.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore
val LightBlue = Color(0xFFCCE5FF)
data class ScheduledDoctor(
    val id: String = "",
    val name: String = "",
    val specialization: String = "",
    val imageResId: Int = R.drawable.img_doctor_1,
    val date: String = "",
    val time: String = ""
)

@Composable
fun ScheduleScreen(navController: NavController, username: String) {
    var scheduled by remember { mutableStateOf<List<ScheduledDoctor>>(emptyList()) }

    LaunchedEffect(true) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(username)
            .collection("scheduledDoctors")
            .get()
            .addOnSuccessListener { result ->
                scheduled = result.documents.map {
                    ScheduledDoctor(
                        id = it.id,
                        name = it.getString("name") ?: "",
                        specialization = it.getString("specialization") ?: "",
                        imageResId = it.getLong("imageResId")?.toInt() ?: R.drawable.img_doctor_1,
                        date = it.getString("date") ?: "",
                        time = it.getString("time") ?: ""
                    )
                }
            }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // 🔙 Back button
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = BluePrimary
                )
            }
            Text(
                text = "Scheduled Doctor",
                fontFamily = poppinsFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (scheduled.isEmpty()) {
            Text(
                text = "No current appointments.",
                fontFamily = poppinsFontFamily,
                color = Color.Gray
            )
        } else {
            scheduled.forEach { doctor ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = LightBlue
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = doctor.imageResId),
                                contentDescription = null,
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = doctor.name,
                                    fontFamily = poppinsFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = doctor.specialization,
                                    fontFamily = poppinsFontFamily,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_date),
                                contentDescription = "Date",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = doctor.date,
                                fontFamily = poppinsFontFamily,
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Icon(
                                painter = painterResource(id = R.drawable.ic_clock),
                                contentDescription = "Time",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = doctor.time,
                                fontFamily = poppinsFontFamily,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // ❌ Cancel Schedule Button
                        Button(
                            onClick = {
                                FirebaseFirestore.getInstance()
                                    .collection("users")
                                    .document(username)
                                    .collection("scheduledDoctors")
                                    .document(doctor.id)
                                    .delete()

                                scheduled = scheduled.filterNot { it.id == doctor.id }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = BluePrimary
                            ),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        ) {
                            Text("Cancel Appointment")
                        }
                    }
                }
            }
        }
    }
}
