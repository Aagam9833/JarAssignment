package com.aagamshah.jarassignment.presentation.onboardingscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.aagamshah.jarassignment.common.Resource
import com.aagamshah.jarassignment.presentation.navigation.Screen
import com.aagamshah.jarassignment.presentation.onboardingscreen.components.IntroScreen
import com.aagamshah.jarassignment.presentation.onboardingscreen.components.OnboardingContent
import com.aagamshah.jarassignment.presentation.onboardingscreen.models.CardContent
import com.aagamshah.jarassignment.presentation.onboardingscreen.models.CtaLottie
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    var expandedCardIndex by remember { mutableIntStateOf(-1) }
    var isDataLoadedAndReady by remember { mutableStateOf(false) }
    var isSequenceComplete by remember { mutableStateOf(false) }
    var cardList by remember { mutableStateOf<List<CardContent>>(emptyList()) }
    var expandCardDelayMs by remember { mutableLongStateOf(0L) }
    var cardAnimationDurationMs by remember { mutableIntStateOf(0) }
    var ctaData by remember { mutableStateOf<CtaLottie?>(null) }
    var showIntroScreen by remember { mutableStateOf(false) }
    var introTitle by remember { mutableStateOf<String?>(null) }
    var introSubtitle by remember { mutableStateOf<String?>(null) }

    when (state) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        is Resource.Success -> {
            state.data?.let { onboardingData ->
                if (cardList.isEmpty()) {
                    cardList = onboardingData.educationCardList.map {
                        CardContent(
                            title = it.expandStateText,
                            imageUri = it.image,
                            startStroke = it.strokeStartColor,
                            endStroke = it.strokeEndColor,
                            backgroundColor = it.backGroundColor,
                            cardBackgroundColor = it.startGradient
                        )
                    }
                    expandCardDelayMs = onboardingData.expandCardStayInterval ?: 8000L
                    cardAnimationDurationMs =
                        (onboardingData.bottomToCenterTranslationInterval ?: 1500L).toInt()
                    val ctaApiData = onboardingData.saveButtonCta
                    val backgroundColor = try {
                        Color(ctaApiData.backgroundColor?.toColorInt() ?: "#272239".toColorInt())
                    } catch (_: Exception) {
                        Color.Black
                    }
                    val textColor = try {
                        Color(ctaApiData.textColor?.toColorInt() ?: "#FDF3D6".toColorInt())
                    } catch (_: Exception) {
                        Color.White
                    }

                    ctaData = CtaLottie(
                        text = ctaApiData.text ?: "Continue",
                        deeplink = ctaApiData.deeplink,
                        backgroundColor = backgroundColor,
                        textColor = textColor,
                        lottieUri = onboardingData.ctaLottie
                    )
                    introTitle = onboardingData.introTitle
                    introSubtitle = onboardingData.introSubtitle
                    isDataLoadedAndReady = true
                }
            }

            if (isDataLoadedAndReady && cardList.isNotEmpty()) {
                LaunchedEffect(Unit) {
                    showIntroScreen = true
                    delay(2000)
                    showIntroScreen = false

                    delay(500)
                    cardList.forEachIndexed { index, _ ->
                        expandedCardIndex = index
                        delay(expandCardDelayMs)
                    }
                    isSequenceComplete = true
                }
            }

            if (showIntroScreen && introTitle != null && introSubtitle != null) {
                IntroScreen(title = introTitle!!, subtitle = introSubtitle!!)
            } else if (!showIntroScreen && isDataLoadedAndReady && cardList.isNotEmpty()) {
                val onCardClick: (Int) -> Unit = { clickedIndex ->
                    if (isSequenceComplete) {
                        expandedCardIndex = clickedIndex
                    }
                }

                OnboardingContent(
                    navController = navController,
                    cardList = cardList,
                    expandedCardIndex = expandedCardIndex,
                    isSequenceComplete = isSequenceComplete,
                    cardAnimationDurationMs = cardAnimationDurationMs,
                    ctaData = ctaData,
                    onCardClick = onCardClick,
                    onCtaClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route)
                        }
                    }
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }

        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = state.message ?: "An unknown error occurred.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
