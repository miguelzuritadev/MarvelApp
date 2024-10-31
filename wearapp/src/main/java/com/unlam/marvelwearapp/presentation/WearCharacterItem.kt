package com.unlam.marvelwearapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.unlam.marvel.domain.model.Character
import com.unlam.marvelwearapp.R
import com.unlam.marvelwearapp.presentation.utils.immersiveListGradient

@Composable
fun WearCharacterItem(
    modifier: Modifier = Modifier,
    character: Character,
    onClick: (Character) -> Unit
) {
    Card(
        onClick = {
            onClick(character)
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(0.5.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.padding(vertical = 4.dp).height(60.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = character.thumbnailUrl,
                contentDescription = "Item",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                alpha = DefaultAlpha,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder_error_image),
                onError = { error ->
                    println("==error: $error==")
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .immersiveListGradient(),
                contentAlignment = Alignment.TopStart,
            ) {
                Column(
                    modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(text = character.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimary)
                    Text(
                        text = character.description, fontSize = 10.sp,
                        maxLines = 1, modifier = modifier.padding(vertical = 4.dp),
                        fontWeight = FontWeight.W300, color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WearCharacterItemPreview() {
    val item = Character(
        id = 1,
        name = "Character Name",
        description = "Description of the character",
        thumbnailUrl = "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
    )
    Column {
        Text(text = "Character Item1", fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary)
        Text(text = "Character Item2", fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary)

        WearCharacterItem(modifier = Modifier, character = item) {

        }
        Text(text = "Character Item3", fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary)
    }

}