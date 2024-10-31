package com.miguelzurita.marveltvapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.unlam.marvel.domain.model.Character
import com.unlam.marvel.domain.model.PreviewModels
import com.unlam.marvelwearapp.presentation.utils.immersiveListGradient

@Composable
fun TvCharacterDetail(card: Character) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .immersiveListGradient(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 58.dp, bottom = 16.dp)
                .width(480.dp)
                .wrapContentHeight()
        ) {
            Text(
                text = card.name,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = card.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(0.8f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemDetailPreview() {
    TvCharacterDetail(PreviewModels.character)
}