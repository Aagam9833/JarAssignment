package com.aagamshah.jarassignment.presentation.onboardingscreen.components

import AppTypography
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aagamshah.jarassignment.presentation.onboardingscreen.models.CtaLottie
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun CtaWithLottie(
    ctaData: CtaLottie,
    onCtaClick: (String?) -> Unit,
    modifier: Modifier = Modifier
) {

    val composition by rememberLottieComposition(
        spec = if (ctaData.lottieUri != null) {
            LottieCompositionSpec.Url(ctaData.lottieUri)
        } else {
            LottieCompositionSpec.Url("")
        }
    )

    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(64.dp))
            .background(ctaData.backgroundColor)
            .clickable { onCtaClick(ctaData.deeplink) }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = ctaData.text,
                color = ctaData.textColor,
                fontWeight = FontWeight.Bold,
                style = AppTypography.labelLarge
            )

            if (ctaData.lottieUri != null) {
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}