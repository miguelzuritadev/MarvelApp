package com.miguelzurita.marveltvapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.tv.material3.Surface
import com.miguelzurita.marveltvapp.ui.ImmersiveListScreen
import com.miguelzurita.marveltvapp.ui.theme.MarvelAppTheme
import com.unlam.marvel.Time
import com.unlam.marvel.domain.LocalCacheManager
import com.unlam.marvel.ui.AppViewModel
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    TvApp()
                }
            }
        }
    }
}

@Composable
fun TvApp(modifier: Modifier = Modifier) {
    val viewModel: AppViewModel by inject(AppViewModel::class.java)
    val localCacheManager: LocalCacheManager by inject(LocalCacheManager::class.java)

    val timestamp = Time.getTimeStamp()

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getLocalCharacters()

            //evaluate ttl before fetching data
            if (localCacheManager.useCache(timestamp)) {
                println("==Data is still fresh==")
                return@launch
            }

            viewModel.getNetworkCharactersAndSaveLocalCache(timestamp)

            localCacheManager.saveTimestamp(timestamp)
        }
    }

    ImmersiveListScreen(viewModel.items.value)
}

@Preview(showBackground = true)
@Composable
fun TvAppPreview() {
    MarvelAppTheme {
        TvApp()
    }
}