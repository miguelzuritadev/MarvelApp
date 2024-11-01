package com.unlam.marvel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.unlam.marvel.domain.LocalCacheManager
import com.unlam.marvel.ui.AppViewModel
import com.unlam.marvel.ui.CharacterLazyVerticalGrid
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

        val timestamp = Time.getTimeStamp()

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val scope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.getCharactersFromLocal()

                    //evaluate ttl before fetching data
                    if (localCacheManager.useCache(timestamp)) {
                        println("==Data is still fresh==")
                        return@launch
                    } else {
                        //clean local cache and get new data
                        println("==Data is stale==")
                    }

                    viewModel.getCharactersFromNetworkAndSaveToLocalCache(timestamp)

                    localCacheManager.saveTimestamp(timestamp)
                }
            }

            CharacterLazyVerticalGrid(viewModel.items.value)
        }
    }
}