package com.example.docapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.docapp.presentation.RootNavGraph
import com.example.docapp.ui.theme.DocAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false) // Optional edge-to-edge

        setContent {
            DocAppTheme {
                RootNavGraph() // ✅ This is the full app nav, with login & main screen
            }
        }
    }
}
