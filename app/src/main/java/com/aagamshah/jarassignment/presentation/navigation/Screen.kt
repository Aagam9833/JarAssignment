package com.aagamshah.jarassignment.presentation.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding_screen")
    object Home : Screen("home_screen")
}