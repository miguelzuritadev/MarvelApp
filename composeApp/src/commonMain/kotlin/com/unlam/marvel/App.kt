package com.unlam.marvel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.unlam.marvel.Crypto.md5
import com.unlam.marvel.network.Character
import com.unlam.marvel.network.MarvelClientImpl
import com.unlam.marvel.network.MarvelRepository
import com.unlam.marvel.ui.CharacterItem
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val items = remember { mutableStateOf(listOf<Character>()) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            val scope = rememberCoroutineScope()
            LaunchedEffect(true) {
                scope.launch {
                    val repository = MarvelRepository(MarvelClientImpl())
                    val timestamp = Time.getTimeStamp()
                    val characters = repository.getCharacters(timestamp, md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY))
                    items.value = characters
                }
            }

            CharacterLazyVerticalGrid(items.value)
        }
    }
}

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