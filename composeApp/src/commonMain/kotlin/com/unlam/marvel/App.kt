package com.unlam.marvel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unlam.marvel.network.Character
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import marvelapp.composeapp.generated.resources.Res
import marvelapp.composeapp.generated.resources.placeholder

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            /*Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val commonGreeting = remember { CommonGreeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $commonGreeting")
                }
            }*/
//            CharacterItemCommon()

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
    }
}

@ExperimentalFoundationApi
@Composable
fun CharacterLazyVerticalGrid(stats: List<Character>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(stats) { index, item ->
            Card(
                modifier = Modifier.padding(8.dp),
                onClick = { },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(0.5.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Image(
                        painterResource(Res.drawable.placeholder),
                        contentDescription = "Item",
                        modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(8.dp)),
                    )

                    Text(
                        text = item.name, fontSize = 17.sp, fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = item.description, fontSize = 15.sp,
                        maxLines = 1, modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        fontWeight = FontWeight.W300
                    )
                }
            }
        }
    }
}