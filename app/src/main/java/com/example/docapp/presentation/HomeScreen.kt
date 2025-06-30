package com.example.docapp.presentation

import androidx.compose.foundation.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.docapp.R
import com.example.docapp.ui.theme.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

// Data class for scheduled doctor
data class Doctor(
    val name: String = "",
    val specialization: String = "",
    val hours: String = "",
    val imageResId: Int = R.drawable.img_doctor_1,
    val date: String = "",
    val time: String = ""
)

@Composable
fun HomeScreen(navController: NavController, username: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 42.dp, start = 16.dp, end = 16.dp),
        color = WhiteBackground
    ) {
        Column {
            // 🧑‍⚕ Header Section
            HeaderContent(username = username)

            Spacer(modifier = Modifier.height(32.dp))

            // 🗓 Title
            Text(
                text = "Your Scheduled Appointments",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // 🩺 Schedule Card if any
            ScheduleContent(username = username)

            Spacer(modifier = Modifier.height(24.dp))

            // 🧭 Menu Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MenuHome(
                    icon = R.drawable.ic_covid,
                    title = "Covid",
                    onClick = { navController.navigate("covid_screen") }
                )
                MenuHome(
                    icon = R.drawable.ic_doctor,
                    title = "Doctors",
                    onClick = { navController.navigate("doctor_list_screen") }
                )
                MenuHome(
                    icon = R.drawable.ic_medicine,
                    title = "Medicine",
                    onClick = { navController.navigate("medicine_notes_screen") }
                )
                MenuHome(
                    icon = R.drawable.ic_hospital,
                    title = "Hospital",
                    onClick = { navController.navigate("hospital_list_screen") }
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
            Spacer(modifier = Modifier.height(32.dp))

            // 💊 Saved Medicine Notes Card
            MedicineCard()

            Spacer(modifier = Modifier.height(32.dp))

            // 💡 Health Tips (if you have them)
            HealthTipsSection()
        }
    }
}

@Composable
private fun HeaderContent(username: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Hello",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.W400,
                color = PurpleGrey
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Hi, $username",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Image(
            painter = painterResource(id = R.drawable.img_header_content),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
    }
}

@Composable
fun ScheduleContent(username: String) {
    var scheduledDoctors by remember { mutableStateOf<List<Doctor>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(true) {
        try {
            val result = FirebaseFirestore.getInstance()
                .collection("users")
                .document(username)
                .collection("scheduledDoctors")
                .get()
                .await()

            scheduledDoctors = result.documents.take(1).map {
                Doctor(
                    name = it.getString("name") ?: "",
                    specialization = it.getString("specialization") ?: "",
                    hours = it.getString("hours") ?: "",
                    imageResId = it.getLong("imageResId")?.toInt() ?: R.drawable.img_doctor_1,
                    date = it.getString("date") ?: "",
                    time = it.getString("time") ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e("FirestoreError", "Failed to load doctor: ${e.message}")
            scheduledDoctors = emptyList()
        } finally {
            isLoading = false
        }
    }

    Column {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            scheduledDoctors.isEmpty() -> {
                Text(
                    text = "No current appointments.",
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            else -> {
                val doctor = scheduledDoctors.firstOrNull()
                if (doctor != null) {
                    DoctorCard(doctor)
                }
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        color = BluePrimary,
        tonalElevation = 1.dp,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = doctor.imageResId),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = doctor.name,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = doctor.specialization,
                        fontFamily = poppinsFontFamily,
                        color = GraySecond,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "📅 ${doctor.date}   ⏰ ${doctor.time}",
                        fontFamily = poppinsFontFamily,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Arrow",
                    tint = Color.White
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(1.dp)
                    .alpha(0.2f),
                color = Color.White
            )

            ScheduleTimeContent()
        }
    }
}
@Composable
fun HealthTipsSection() {
    val tips = listOf(
        "💧 Stay hydrated. Drink 8 glasses of water!",
        "🧘‍♀️ Take deep breaths and relax.",
        "🍎 Eat fruits & veggies daily.",
        "😴 Get 7–8 hours of sleep.",
        "🚶 Walk daily to stay active!"
    )

    Column {
        Text(
            text = "Daily Health Tips",
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(tips) { tip ->
                HealthTipCard(tip)
            }
        }
    }
}

@Composable
fun HealthTipCard(tip: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = LightBlue,
        modifier = Modifier
            .width(280.dp)
            .heightIn(min = 100.dp)
    ) {
        Text(
            text = tip,
            fontFamily = poppinsFontFamily,
            fontSize = 14.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}
@Composable
fun MedicineCard() {
    // Replace this with ViewModel/firestore if needed
    val sharedPreferences = LocalContext.current.getSharedPreferences("med_notes", 0)
    val savedTitle = sharedPreferences.getString("title", null)
    val savedNotes = sharedPreferences.getString("note", null)

    if (!savedTitle.isNullOrEmpty() || !savedNotes.isNullOrEmpty()) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = LightBlue,
            shadowElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "💊 ${savedTitle ?: "My Medicines"}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    color = BluePrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = savedNotes ?: "",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.Black
                )
            }
        }
    }
}