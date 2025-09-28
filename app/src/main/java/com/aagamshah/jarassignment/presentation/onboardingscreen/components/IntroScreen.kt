package com.aagamshah.jarassignment.presentation.onboardingscreen.components

import AppTypography
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aagamshah.jarassignment.ui.theme.Color201929
import com.aagamshah.jarassignment.ui.theme.ColorF8DC83

@Composable
fun IntroScreen(title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color201929),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                style = AppTypography.titleMedium
            )
            Text(
                text = subtitle,
                color = ColorF8DC83,
                style = AppTypography.titleLarge
            )
        }
    }
}