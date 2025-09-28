package com.aagamshah.jarassignment.presentation.onboardingscreen.components

import AppTypography
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

private const val ANIM_DURATION_MS = 700

@Composable
fun OnboardingEducationCard(
    title: String,
    imageUri: String,
    startStroke: String,
    endStroke: String,
    cardBackgroundColor: String,
    isCardExpanded: Boolean,
    isClickable: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val collapsedImageSize = 40.dp
    val collapsedCornerRadius = collapsedImageSize / 2

    val cardMaxHeight by animateDpAsState(
        targetValue = if (isCardExpanded) 480.dp else 80.dp,
        animationSpec = tween(ANIM_DURATION_MS)
    )

    val imageSize by animateDpAsState(
        targetValue = if (isCardExpanded) 360.dp else collapsedImageSize,
        animationSpec = tween(ANIM_DURATION_MS)
    )
    val imageCornerRadius by animateDpAsState(
        targetValue = if (isCardExpanded) 12.dp else collapsedCornerRadius,
        animationSpec = tween(ANIM_DURATION_MS)
    )
    val imageMargin by animateDpAsState(
        targetValue = if (isCardExpanded) 16.dp else 0.dp,
        animationSpec = tween(ANIM_DURATION_MS)
    )

    val expandedStrokeBrush = remember {
        Brush.verticalGradient(
            colors = listOf(Color(startStroke.toColorInt()), Color(endStroke.toColorInt()))
        )
    }

    val currentStrokeBrush = if (isCardExpanded) expandedStrokeBrush else Brush.linearGradient(
        listOf(
            Color.White,
            Color.White
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = cardMaxHeight)
            .border(
                width = if (isCardExpanded) 1.dp else 0.dp,
                brush = currentStrokeBrush,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(enabled = isClickable) {
                if (isClickable) onCardClick()
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(cardBackgroundColor.toColorInt()))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                if (isCardExpanded) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ImageBlock(
                            imageUri,
                            imageSize,
                            imageCornerRadius,
                            Modifier.padding(bottom = imageMargin)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TitleBlock(title, true, Modifier.fillMaxWidth())
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ImageBlock(
                            imageUri,
                            imageSize,
                            imageCornerRadius,
                            Modifier.padding(16.dp)
                        )
                        TitleBlock(title, false, Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                if (isClickable) onCardClick()
                            },
                            enabled = isClickable,
                            modifier = Modifier
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "Expand",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ImageBlock(imageUri: String, size: Dp, cornerRadius: Dp, modifier: Modifier) {

    val context = LocalContext.current

    val maxImageSizePx = with(LocalResources.current.displayMetrics) {
        (360.dp.value * density).toInt()
    }

    val imageRequest = remember(imageUri, context) {
        ImageRequest.Builder(context)
            .data(imageUri)
            .size(maxImageSizePx)
            .build()
    }

    AsyncImage(
        model = imageRequest,
        contentDescription = "Card Image",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
    )
}

@Composable
private fun TitleBlock(title: String, isExpanded: Boolean, modifier: Modifier) {
    Text(
        text = title,
        style = if (isExpanded) AppTypography.titleMedium else AppTypography.labelLarge,
        textAlign = if (isExpanded) TextAlign.Center else TextAlign.Start,
        maxLines = if (!isExpanded) 1 else 2,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}