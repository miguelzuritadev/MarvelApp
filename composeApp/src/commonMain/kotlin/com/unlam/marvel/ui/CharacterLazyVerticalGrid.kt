package com.unlam.marvel.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.unlam.marvel.domain.model.Character


@ExperimentalFoundationApi
@Composable
fun CharacterLazyVerticalGrid(data: List<Character>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        itemsIndexed(data) { index, item ->
            CharacterItem(Modifier, item) {
                println("==index: $index==")
            }
        }
    }
}