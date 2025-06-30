package com.example.docapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.docapp.R
import com.example.docapp.ui.theme.BluePrimary
import com.example.docapp.ui.theme.poppinsFontFamily
import com.google.firebase.firestore.FirebaseFirestore

// --------------------------------------
// 👩‍⚕️ Doctor data class (with image + schedule info)
// --------------------------------------

@Composable
fun DoctorListScreen(navController: NavController, username: String) {
    val doctorList = listOf(
        Doctor("Dr. Aashi Mehta", "Cardiologist", "10:00 AM - 4:00 PM", R.drawable.img_doctor_1),
        Doctor("Dr. Rohan Das", "Dermatologist", "11:00 AM - 5:00 PM", R.drawable.img_doctor_2),
        Doctor("Dr. Neha Sharma", "Neurologist", "09:00 AM - 3:00 PM", R.drawable.img_doctor_3),
        Doctor("Dr. Arjun Kapoor", "Orthopedic", "12:00 PM - 6:00 PM", R.drawable.img_doctor_4),
        Doctor("Dr. Priya Verma", "Pediatrician", "10:30 AM - 4:30 PM", R.drawable.img_doctor_5),
        Doctor("Dr. Kabir Sinha", "Psychiatrist", "01:00 PM - 7:00 PM", R.drawable.img_doctor_6),
        Doctor("Dr. Isha Roy", "Gynecologist", "08:00 AM - 2:00 PM", R.drawable.img_doctor_7)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🔙 BACK BUTTON
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = BluePrimary
                )
            }
            Text(
                text = "Available Doctors",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(doctorList) { doctor ->
            DoctorCard(doctor = doctor, username = username)
        }
    }
        }
}

@Composable
fun DoctorCard(doctor: Doctor, username: String) {
    val db = FirebaseFirestore.getInstance()

    var showDialog by remember { mutableStateOf(false) }
    var step by remember { mutableStateOf(1) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = doctor.imageResId),
                    contentDescription = doctor.name,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 16.dp)
                )
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
                    Text(
                        text = "Hours: ${doctor.hours}",
                        fontSize = 13.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    showDialog = true
                    step = 1
                },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Text("Schedule Appointment", color = Color.White)
            }
        }
    }

    // -----------------------
    // 📅 Calendar + Time Dialog
    // -----------------------
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (step == 1 && selectedDate.isNotEmpty()) {
                        step = 2
                    } else if (step == 2 && selectedTime.isNotEmpty()) {
                        // ✅ Save to Firestore
                        val appointment = hashMapOf(
                            "name" to doctor.name,
                            "specialization" to doctor.specialization,
                            "hours" to doctor.hours,
                            "date" to selectedDate,
                            "time" to selectedTime,
                            "imageResId" to doctor.imageResId
                        )
                        db.collection("users")
                            .document(username)
                            .collection("scheduledDoctors")
                            .document("${doctor.name}-${selectedDate}")
                            .set(appointment)

                        showDialog = false
                        selectedDate = ""
                        selectedTime = ""
                        step = 1
                    }
                }) {
                    Text(if (step == 1) "Next" else "Schedule")
                }
            },
            title = {
                Text(
                    text = if (step == 1) "Select Appointment Date" else "Select Time Slot",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                if (step == 1) {
                    Column {
                        listOf("July 1", "July 2", "July 3", "July 4").forEach { date ->
                            Text(
                                text = date,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable { selectedDate = date },
                                color = if (selectedDate == date) BluePrimary else Color.Black
                            )
                        }
                    }
                } else {
                    Column {
                        listOf("10:00 AM", "12:00 PM", "3:00 PM", "5:00 PM").forEach { time ->
                            Text(
                                text = time,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable { selectedTime = time },
                                color = if (selectedTime == time) BluePrimary else Color.Black
                            )
                        }
                    }
                }
            }
        )
    }
}
