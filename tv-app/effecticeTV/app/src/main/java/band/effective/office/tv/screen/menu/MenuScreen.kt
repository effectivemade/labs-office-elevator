package band.effective.office.tv.screen.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Text
import band.effective.office.tv.R
import band.effective.office.tv.screen.menu.component.ButtonAutoplay
import band.effective.office.tv.screen.menu.component.MenuComponent
import band.effective.office.tv.screen.menu.component.TimeComponent
import band.effective.office.tv.screen.navigation.NavigationModel
import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.ui.theme.robotoFontFamily
import kotlinx.coroutines.launch

@Composable
fun MenuScreen(
    itemsList: List<NavigationModel>, navController: NavController = rememberNavController()
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        val boxScope = this
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(boxScope.minHeight / 4))
            Text(
                text = stringResource(R.string.welcome_string),
                fontFamily = robotoFontFamily(),
                fontSize = 50.sp,
                color = Color.White
            )
            Spacer(Modifier.height(20.dp))
            ButtonAutoplay(
                text = stringResource(id = R.string.autoplay_button_text),
                onFocus = {
                    coroutineScope.launch { scrollState.animateScrollTo(0) }
                },
                onClick = { navController.navigate(Screen.AutoplayMenu.name) })
            Spacer(Modifier.height(20.dp))
            MenuComponent(
                itemsList = itemsList.map { Pair(it.screen, it.title) },
                onNavigate = { navController.navigate(it.name) })
        }
    }
    TimeComponent(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    )
}