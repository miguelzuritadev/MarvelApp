package com.unlam.marvel.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unlam.marvel.network.Character

@Composable
fun CharacterList(data: ArrayList<Character>, isLoading: Boolean) {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
        Column {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    items(data.size) { i ->
                        CharacterItem(character = data[i], onClick = {})
                    }
                }
            }

            if (isLoading) {
                CenteredLoadingIndicator()
            }
        }
    }
}

@Composable
fun CenteredLoadingIndicator() {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiaryContainer)
    }
}

@Preview(showBackground = true)
@Composable
fun CenteredLoadingIndicatorPreview() {
    CenteredLoadingIndicator()
}

@Preview(showBackground = true)
@Composable
fun CharacterListPreview() {
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
    val items = arrayListOf(element1, element2)

    CharacterList(data = items, isLoading = false)
}