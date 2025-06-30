package com.example.docapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // 🔐 Register user and save to Firestore
    fun registerUser(
        name: String,
        username: String,
        email: String,
        contact: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit
    ) {
        if (name.isBlank() || username.isBlank() || email.isBlank() || contact.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val user = hashMapOf(
            "name" to name,
            "username" to username,
            "email" to email,
            "contact" to contact,
            "password" to password // 🔒 Plain text for now — hash later if needed
        )

        viewModelScope.launch {
            db.collection("users")
                .document(username)
                .set(user)
                .addOnSuccessListener {
                    Toast.makeText(context, "Registration successful 🎉", Toast.LENGTH_SHORT).show()
                    onSuccess()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to register: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // 🔑 Login with username & password
    fun loginUser(
        username: String,
        password: String,
        context: Context,
        onSuccess: (String) -> Unit
    ) {
        if (username.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            db.collection("users")
                .document(username)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val storedPassword = document.getString("password")
                        if (storedPassword == password) {
                            Toast.makeText(context, "Login successful 🎉", Toast.LENGTH_SHORT).show()
                            onSuccess(username)
                        } else {
                            Toast.makeText(context, "Incorrect password 😬", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "User not found 😢", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Login failed: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
