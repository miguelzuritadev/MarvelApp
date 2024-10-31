package com.miguelzurita.marveltvapp.utils

import androidx.compose.ui.Modifier

fun Modifier.checkCondition(
    condition: Boolean,
    trueModifier: Modifier,
    falseModifier: Modifier = Modifier
): Modifier = then(if (condition) trueModifier else falseModifier)