package com.miguelzurita.marveltvapp.ui

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.CompactCard
import coil3.compose.AsyncImage
import com.miguelzurita.marveltvapp.R
import com.miguelzurita.marveltvapp.utils.checkCondition
import com.unlam.marvel.domain.model.Character
import com.unlam.marvel.domain.model.PreviewModels


@Composable
fun TvCharacterItem(index: Int, firstChildFocusRequester: FocusRequester, card: Character, onFocusChangedTemp: ((FocusState) -> Unit)? = null) {
    CompactCard(
        modifier = Modifier
            .width(196.dp)
            .aspectRatio(16f / 9)
            .checkCondition(index == 0, Modifier.focusRequester(firstChildFocusRequester))
            .onFocusChanged {
                onFocusChangedTemp?.invoke(it)
            },
        onClick = {},
        image = {
            AsyncImage(
                model = card.thumbnailUrl,
                contentDescription = "Character",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(4.dp)),
                alignment = Alignment.Center,
                alpha = DefaultAlpha,
                filterQuality = DrawScope.DefaultFilterQuality,
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder_error_image),
                onError = { error ->
                    println("==error: $error==")
                }
            )
        },
        title = {},
        colors = CardDefaults.colors(containerColor = Color.Transparent)
    )
}

@Preview(showBackground = true)
@Composable
fun RenderCompactCardPreview() {
    TvCharacterItem(0, FocusRequester(), PreviewModels.character)
}