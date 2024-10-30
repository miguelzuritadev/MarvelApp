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
import com.unlam.marvel.data.local.LocalCharacter
import com.unlam.marvel.data.local.LocalCharacterDBRepositoryImp
import com.unlam.marvel.domain.model.Character
import com.unlam.marvel.data.network.MarvelClientImpl
import com.unlam.marvel.data.network.MarvelRepository
import com.unlam.marvel.preferences.DataStoreRepository
import com.unlam.marvel.preferences.DataStoreWrapper
import com.unlam.marvel.ui.AppViewModel
import com.unlam.marvel.ui.CharacterItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.java.KoinJavaComponent.inject


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel: AppViewModel by inject(AppViewModel::class.java)

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val scope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.getLocalCharacters()

                    val dataStoreWrapper = DataStoreWrapper()
                    val dataStoreRepository = DataStoreRepository(dataStoreWrapper.resolveDataStore())
                    val dataStoreTimestamp = dataStoreRepository.readTimestamp().first()
                    println("==timestamp: $dataStoreTimestamp==")

                    //evaluate ttl before fetching data
                    val currentTime = System.currentTimeMillis()
                    val ttl = 1000 * 60 * 2 // 2 minutes
                    val differenceMs = currentTime - dataStoreTimestamp
                    println("differenceMs: $differenceMs")
                    if (differenceMs < ttl) {
                        println("==Data is still fresh==")
                        return@launch
                    } else {
                        //clean local cache and get new data
                        println("==Data is stale==")
                    }

                    val repository = MarvelRepository(MarvelClientImpl())
                    val timestamp = Time.getTimeStamp()
                    val characters = repository.getCharacters(timestamp, md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY))
                    viewModel.items.value = characters

                    viewModel.saveLocalCharacters(characters)

                    dataStoreRepository.saveTimestamp(currentTime)
                }
            }

            CharacterLazyVerticalGrid(viewModel.items.value)
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