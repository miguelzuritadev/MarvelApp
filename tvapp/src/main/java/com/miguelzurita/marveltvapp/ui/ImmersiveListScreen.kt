package com.miguelzurita.marveltvapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.miguelzurita.marveltvapp.R
import com.unlam.marvel.domain.model.Character

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImmersiveListScreen(characterList: List<Character>) {
    var selectedCharacter by remember { mutableStateOf(characterList.firstOrNull()) }

    // Reset selectedCharacter to the first item on recomposition if characterList changes
    LaunchedEffect(characterList) {
        selectedCharacter = characterList.firstOrNull() ?: selectedCharacter
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // background image
        selectedCharacter?.let { item ->
            AsyncImage(
                model = item.thumbnailUrl,
                contentDescription = "Character",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                alpha = DefaultAlpha,
                filterQuality = DrawScope.DefaultFilterQuality,
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder_error_image),
                onError = { error ->
                    println("==error: $error==")
                }
            )

            // gradient and text
            TvCharacterDetail(item)
        }

        val firstChildFocusRequester = remember { FocusRequester() }

        LazyRow(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 20.dp)
                .focusRestorer { firstChildFocusRequester },
            contentPadding = PaddingValues(start = 58.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsIndexed(characterList) { index, item ->
                TvCharacterItem(index, firstChildFocusRequester, item) {
                    if (it.isFocused) {
                        selectedCharacter = item
                    }
                }
            }
        }
    }
}



