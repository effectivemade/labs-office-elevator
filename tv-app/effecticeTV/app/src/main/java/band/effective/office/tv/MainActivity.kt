package band.effective.office.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import band.effective.office.tv.screen.LeaderIdEvets.LeaderIdEventsScreen
import band.effective.office.tv.screen.Load.LoadScreen
import band.effective.office.tv.ui.theme.EffecticeTVTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isLoad by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                while(true) {
                    delay(3000)
                    isLoad = true
                }
            }
            EffecticeTVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if (isLoad) LeaderIdEventsScreen()
                    else LoadScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    TvLazyRow (
        contentPadding = PaddingValues(16.dp),
        userScrollEnabled = true
        ){
        items(100) {
            Image(
                modifier = Modifier.clickable {  },
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(32.dp))
        }
    }
}

