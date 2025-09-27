package com.aagamshah.jarassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.aagamshah.jarassignment.presentation.navigation.AppNavigation
import com.aagamshah.jarassignment.ui.theme.JarAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JarAssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
