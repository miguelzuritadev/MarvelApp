package com.unlam.marvel

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.unlam.marvel.domain.model.Character

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "MarvelApp") {
        App()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview()
@Composable
fun CharacterLazyVerticalGridPreview() {
    val element1 = Character(
        id = 1,
        name = "A.I.M.",
        description = "Advanced Idea Mechanics",
        thumbnailUrl = "https://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec.jpg"
    )
    val element2 = Character(
        id = 1,
        name = "B.B.",
        description = "Business Border",
        thumbnailUrl = "https://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec.jpg"
    )
    val items = arrayListOf(element1, element2, element1, element2, element1, element2)
    CharacterLazyVerticalGrid(items)
}