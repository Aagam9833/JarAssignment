package com.aagamshah.jarassignment.presentation.onboardingscreen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.aagamshah.jarassignment.presentation.common.TopToolbar
import com.aagamshah.jarassignment.presentation.onboardingscreen.models.CardContent
import com.aagamshah.jarassignment.presentation.onboardingscreen.models.CtaLottie

@Composable
fun OnboardingContent(
    navController: NavController,
    cardList: List<CardContent>,
    expandedCardIndex: Int,
    isSequenceComplete: Boolean,
    cardAnimationDurationMs: Int,
    onCardClick: (Int) -> Unit,
    ctaData: CtaLottie?,
    onCtaClick: (String?) -> Unit
) {
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
                "201929"
            }
        } else {
            "201929"
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
        animationSpec = tween(cardAnimationDurationMs),
        label = "GradientStartAnimation"
    )
    val animatedEndColor by animateColorAsState(
        targetValue = gradientEndColor,
        animationSpec = tween(cardAnimationDurationMs),
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
            TopToolbar(modifier = Modifier, { navController.popBackStack() }, "Onboarding")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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
                        animationSpec = tween(cardAnimationDurationMs),
                        label = "YOffsetAnimation"
                    )
                    val targetOpacity =
                        if (index <= expandedCardIndex || isSequenceComplete) 1f else 0f
                    val opacity by animateFloatAsState(
                        targetValue = targetOpacity,
                        animationSpec = tween(cardAnimationDurationMs),
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
            if (isSequenceComplete && ctaData != null) {
                CtaWithLottie(
                    ctaData = ctaData,
                    onCtaClick = onCtaClick,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(vertical = 16.dp)
                )
            }
        }
    }
}