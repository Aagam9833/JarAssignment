package com.aagamshah.jarassignment.presentation.homescreen

import AppTypography
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.aagamshah.jarassignment.presentation.common.TopToolbar

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        containerColor = Color("#201929".toColorInt())
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            TopToolbar(modifier = Modifier, { navController.popBackStack() }, "Landing Page")
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Landing Page",
                style = AppTypography.titleMedium,
                color = Color.White
            )
        }
    }
}