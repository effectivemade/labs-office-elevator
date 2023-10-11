package band.effective.office.tv.screen.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Text
import band.effective.office.tv.R
import band.effective.office.tv.screen.autoplayMenu.ItemRes
import band.effective.office.tv.screen.menu.component.MenuComponent
import band.effective.office.tv.screen.menu.component.PlayButton
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
            PlayButton(
                modifier = Modifier
                    .height(60.dp)
                    .width(180.dp)
                    .clip(shape = RoundedCornerShape(230.dp)),
                text = stringResource(id = R.string.autoplay_button),
                onFocus = {
                    coroutineScope.launch { scrollState.animateScrollTo(0) }
                },
                onClick = { navController.navigate(Screen.AutoplayMenu.name) })
            Spacer(Modifier.height(20.dp))
            MenuComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                itemsList = itemsList.map { Pair(it.screen, ItemRes(text = it.title)) },
                onNavigate = { navController.navigate(it.name) })
        }
    }
    TimeComponent(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    )
}