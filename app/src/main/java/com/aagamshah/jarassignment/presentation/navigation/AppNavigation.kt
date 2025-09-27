package com.aagamshah.jarassignment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aagamshah.jarassignment.presentation.onboardingscreen.OnboardingScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(route = Screen.Onboarding.route) { OnboardingScreen() }
        composable(route = Screen.Home.route) {}
    }
}
