package com.aagamshah.jarassignment.presentation.onboardingscreen.models

import androidx.compose.ui.graphics.Color

data class CtaLottie(
    val text: String,
    val deeplink: String?,
    val backgroundColor: Color,
    val textColor: Color,
    val lottieUri: String?
)