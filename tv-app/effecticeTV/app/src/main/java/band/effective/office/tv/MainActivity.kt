package band.effective.office.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import band.effective.office.tv.screen.navigation.NavigationHost
import band.effective.office.tv.ui.theme.EffecticeTVTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EffecticeTVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationHost()
                }
            }
        }
    }
}

@Composable
fun TemporaryScreen() {
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

