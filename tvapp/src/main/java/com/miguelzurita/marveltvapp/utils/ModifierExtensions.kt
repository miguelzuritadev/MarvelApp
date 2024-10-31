package com.unlam.marvelwearapp.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.tv.material3.MaterialTheme

fun Modifier.immersiveListGradient(): Modifier = composed {
    val color = MaterialTheme.colorScheme.scrim

    val verticalColorAlphaList = listOf(1.0f, 0.0f)
    val verticalColorStopList = listOf(0.2f, 0.65f)

    val horizontalAlphaList = listOf(1.0f, 0.0f)
    val horizontalColorStopList = listOf(0.1f, 0.8f)
    this
        //horizontal gradient
        .then(
            background(
                brush = Brush.linearGradient(
                    verticalColorStopList[0] to color.copy(alpha = verticalColorAlphaList[0]),
                    verticalColorStopList[1] to color.copy(alpha = verticalColorAlphaList[1]),
                    start = Offset(0.0f, 0.0f),
                    end = Offset(Float.POSITIVE_INFINITY, 0.0f)
                )
            )
        )
        //vertical gradient
        .then(
            background(
                brush = Brush.linearGradient(
                    horizontalColorStopList[0] to color.copy(alpha = horizontalAlphaList[0]),
                    horizontalColorStopList[1] to color.copy(alpha = horizontalAlphaList[1]),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(0f, 0f)
                )
            )
        )
}
