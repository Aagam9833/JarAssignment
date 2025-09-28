package com.aagamshah.jarassignment.presentation.onboardingscreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background // ⭐️ New Import
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box // ⭐️ New Import
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush // ⭐️ New Import
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.aagamshah.jarassignment.common.Resource
import com.aagamshah.jarassignment.presentation.onboardingscreen.components.OnboardingEducationCard
import com.aagamshah.jarassignment.presentation.onboardingscreen.models.CardContent
import kotlinx.coroutines.delay

private const val EXPANDED_CARD_DELAY_MS = 2000L
private const val CARD_ANIMATION_DURATION = 1500

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    var expandedCardIndex by remember { mutableIntStateOf(-1) }
    var isDataLoadedAndReady by remember { mutableStateOf(false) }
    var isSequenceComplete by remember { mutableStateOf(false) }
    var cardList by remember { mutableStateOf<List<CardContent>>(emptyList()) }

    val baseColorHex = remember(expandedCardIndex, cardList) {
        if (expandedCardIndex in cardList.indices) {
            val fullHex = cardList[expandedCardIndex].backgroundColor
            if (fullHex.startsWith("#") && fullHex.length > 6) {
                fullHex.substring(fullHex.length - 6)
            } else if (fullHex.startsWith("#") && fullHex.length == 7) {
                fullHex.substring(1)
            } else if (fullHex.length == 6) {
                fullHex
            } else {
                "000000"
            }
        } else {
            "000000"
        }
    }

    val gradientStartColorHex = remember(baseColorHex) { "#33$baseColorHex" }
    val gradientEndColorHex = remember(baseColorHex) { "#CC$baseColorHex" }

    val gradientStartColor = remember(gradientStartColorHex) {
        try {
            Color(gradientStartColorHex.toColorInt())
        } catch (_: Exception) {
            Color.Black.copy(alpha = 0.2f)
        }
    }
    val gradientEndColor = remember(gradientEndColorHex) {
        try {
            Color(gradientEndColorHex.toColorInt())
        } catch (_: Exception) {
            Color.Black.copy(alpha = 0.8f)
        }
    }

    val animatedStartColor by animateColorAsState(
        targetValue = gradientStartColor,
        animationSpec = tween(CARD_ANIMATION_DURATION),
        label = "GradientStartAnimation"
    )
    val animatedEndColor by animateColorAsState(
        targetValue = gradientEndColor,
        animationSpec = tween(CARD_ANIMATION_DURATION),
        label = "GradientEndAnimation"
    )

    val animatedBackgroundBrush = Brush.verticalGradient(
        listOf(animatedStartColor, animatedEndColor)
    )

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(animatedBackgroundBrush)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when (state) {
                    is Resource.Loading -> {
                        CircularProgressIndicator(color = Color.White)
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
                                isDataLoadedAndReady = true
                            }
                        }

                        if (isDataLoadedAndReady && cardList.isNotEmpty()) {
                            LaunchedEffect(cardList) {
                                delay(500)
                                cardList.forEachIndexed { index, _ ->
                                    expandedCardIndex = index
                                    delay(EXPANDED_CARD_DELAY_MS)
                                }
                                isSequenceComplete = true
                            }
                        }

                        val onCardClick: (Int) -> Unit = { clickedIndex ->
                            if (isSequenceComplete) {
                                expandedCardIndex = if (expandedCardIndex == clickedIndex) {
                                    expandedCardIndex
                                } else {
                                    clickedIndex
                                }
                            }
                        }

                        cardList.forEachIndexed { index, cardData ->
                            val isCardExpanded = index == expandedCardIndex
                            val isSequenceStarted = expandedCardIndex != -1 || isSequenceComplete
                            val targetYOffset = when {
                                !isSequenceStarted -> 1000f
                                index <= expandedCardIndex -> 0f
                                else -> 1000f
                            }
                            val initialYOffset by animateFloatAsState(
                                targetValue = targetYOffset,
                                animationSpec = tween(CARD_ANIMATION_DURATION),
                                label = "YOffsetAnimation"
                            )
                            val targetOpacity =
                                if (index <= expandedCardIndex || isSequenceComplete) 1f else 0f
                            val opacity by animateFloatAsState(
                                targetValue = targetOpacity,
                                animationSpec = tween(CARD_ANIMATION_DURATION),
                                label = "OpacityAnimation"
                            )
                            val cardModifier = if (!isSequenceComplete) {
                                Modifier.graphicsLayer {
                                    translationY = initialYOffset
                                    alpha = opacity
                                }
                            } else {
                                Modifier
                            }

                            OnboardingEducationCard(
                                title = cardData.title,
                                imageUri = cardData.imageUri,
                                startStroke = cardData.startStroke,
                                endStroke = cardData.endStroke,
                                isCardExpanded = isCardExpanded,
                                isClickable = isSequenceComplete,
                                onCardClick = { onCardClick(index) },
                                modifier = cardModifier,
                                cardBackgroundColor = cardData.cardBackgroundColor
                            )

                            val isLastCard = index == cardList.lastIndex

                            if (isCardExpanded) {
                                if (!isLastCard) {
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            } else {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    is Resource.Error -> {
                        Text(
                            text = state.message ?: "An unknown error occurred.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}