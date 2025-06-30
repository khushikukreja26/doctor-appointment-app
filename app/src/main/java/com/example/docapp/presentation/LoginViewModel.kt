package com.example.docapp.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel : ViewModel() {

    // Stores whether the user is signed in or not
    var signedIn by mutableStateOf(false)
        private set

    // Stores user information
    var user by mutableStateOf<FirebaseUser?>(null)
        private set

    /**
     * Sign in using the Google ID token obtained from the Google Sign-In Intent
     */
    fun signInWithGoogle(idToken: String, context: Context, onSuccess: () -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user = FirebaseAuth.getInstance().currentUser
                    signedIn = true
                    onSuccess()
                } else {
                    Toast.makeText(context, "Authentication Failed 😔", Toast.LENGTH_SHORT).show()
                }
            }
    }

    /**
     * Sign the user out of Firebase
     */
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
        signedIn = false
        user = null
    }
}
