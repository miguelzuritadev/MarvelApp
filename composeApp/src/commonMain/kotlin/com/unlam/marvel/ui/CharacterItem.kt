package com.unlam.marvel.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.unlam.marvel.network.Character
import marvelapp.composeapp.generated.resources.Res
import marvelapp.composeapp.generated.resources.placeholder
import marvelapp.composeapp.generated.resources.placeholder_error_image
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    character: Character, onClick: (Character) -> Unit
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
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            /*AsyncImage(
                imageUrl = game.thumbnailUrl,
                contentDescription = "Game",
                contentScale = ContentScale.Crop,
                loadingPlaceHolder = {},
                modifier = modifier.fillMaxWidth().height(220.dp),
                errorPlaceHolder = {},
                alignment = Alignment.Center,
                alpha = DefaultAlpha,
                coloFilter = null,
                filterQuality = DrawScope.DefaultFilterQuality,
            )*/

            AsyncImage(
                model = character.thumbnailUrl,
                contentDescription = "Item",
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopStart,
                modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(8.dp)),
                placeholder = painterResource(Res.drawable.placeholder),
                error = painterResource(Res.drawable.placeholder_error_image)
            )

            Row(
                modifier.fillMaxWidth()
                    .padding(top = 16.dp, bottom = 0.dp, start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = character.name, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            }

            Text(
                text = character.description, fontSize = 15.sp,
                maxLines = 1, modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                fontWeight = FontWeight.W300
            )
        }
    }
}
