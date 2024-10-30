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
import com.unlam.marvel.domain.model.Character
import com.unlam.marvel.data.network.MarvelClientImpl
import com.unlam.marvel.data.network.MarvelRepository
import com.unlam.marvel.domain.LocalCacheManager
import com.unlam.marvel.preferences.DataStoreRepository
import com.unlam.marvel.ui.AppViewModel
import com.unlam.marvel.ui.CharacterItem
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.java.KoinJavaComponent.inject


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel: AppViewModel by inject(AppViewModel::class.java)
        val localCacheManager: LocalCacheManager by inject(LocalCacheManager::class.java)

        val currentTime = System.currentTimeMillis()

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val scope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.getLocalCharacters()

                    //evaluate ttl before fetching data
                    if (localCacheManager.useCache(currentTime)) {
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

                    localCacheManager.saveTimestamp(currentTime)
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