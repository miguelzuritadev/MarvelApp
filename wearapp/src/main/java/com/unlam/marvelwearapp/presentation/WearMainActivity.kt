/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.unlam.marvelwearapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.unlam.marvel.CommonGreeting
import com.unlam.marvel.Time
import com.unlam.marvel.domain.LocalCacheManager
import com.unlam.marvel.ui.AppViewModel
import com.unlam.marvelwearapp.R
import com.unlam.marvelwearapp.presentation.theme.MarvelAppTheme
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            MarvelAppTheme {
                WearApp("Marvel APP")
            }
        }
    }
}

@Composable
fun WearApp(header: String) {

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
            } else {
                //clean local cache and get new data
                println("==Data is stale==")
            }

            viewModel.getNetworkCharactersAndSaveLocalCache(timestamp)

            localCacheManager.saveTimestamp(timestamp)
        }
    }

    ScalingLazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.background),
    ) {
        item {
            Text(
                text = header,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        }

        items(viewModel.items.value.size) { i ->
            Text(
                text = viewModel.items.value[i].name,
                fontSize = 18.sp,
            )
            /*GameWearItem(game = state.allGames[i], onClick = {
                //navigate to details todo
            })*/
        }
    }
}


@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}