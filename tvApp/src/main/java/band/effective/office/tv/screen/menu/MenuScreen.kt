package band.effective.office.tv.screen.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Text
import band.effective.office.tv.R
import band.effective.office.tv.screen.menu.component.PlayButton
import band.effective.office.tv.screen.navigation.NavigationModel
import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.ui.theme.robotoFontFamily
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import kotlinx.coroutines.launch

@Composable
fun ScreenList(
    modifier: Modifier = Modifier,
    isFocusOnMenu: Boolean,
    content: @Composable BoxScope.(Int) -> Unit
) {
    var index by remember { mutableStateOf(0) }
    var currentIndex by remember { mutableStateOf(0) }
    val iconList = listOf(
        Icons.Default.PlayArrow,
        Icons.Default.Home,
        Icons.Default.AccountBox
    )// TODO add true icon
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            iconList.forEachIndexed { iconIndex, icon ->
                if (iconIndex != 0) Spacer(modifier = Modifier.height(5.dp))
                val elementColor = when {
                    !isFocusOnMenu && currentIndex == iconIndex -> MaterialTheme.colors.secondaryVariant
                    currentIndex == iconIndex -> MaterialTheme.colors.primaryVariant
                    else -> Color.Transparent
                }
                Row(
                    modifier = Modifier
                        .onFocusChanged { if (it.isFocused) currentIndex = iconIndex }
                        .clip(RoundedCornerShape(10.dp))
                        .background(elementColor)
                        .clickable { index = iconIndex },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Image(
                        modifier = Modifier.size(40.dp),
                        imageVector = icon,
                        contentDescription = null
                    )
                    if (isFocusOnMenu) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = "Autoplay", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
        content(index)
    }
}

@Composable
fun MainMenuPlaceHolder(text: String) {
    Text(
        text = text,
        fontFamily = robotoFontFamily(),
        fontSize = 50.sp,
        color = Color.White
    )
    Spacer(Modifier.height(20.dp))
    Text(
        text = "Скоро",
        fontFamily = robotoFontFamily(),
        fontSize = 25.sp,
        color = Color.White
    )
}

@Composable
fun MenuScreen(
    itemsList: List<NavigationModel>, navController: NavController = rememberNavController()
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var isFocusOnMenu by remember { mutableStateOf(false) }
    val focusRequester = FocusRequester()

    ScreenList(
        modifier = Modifier.fillMaxSize(),
        isFocusOnMenu = isFocusOnMenu
    ) { index ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            Image(
                modifier = Modifier.fillMaxSize().offset(y = -30.dp),
                painter = painterResource(id = R.drawable.ellipse_orange),
                contentDescription = null
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
            Image(
                modifier = Modifier.fillMaxSize().offset(x = 70.dp),
                painter = painterResource(id = R.drawable.ellipse_purple),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (index) {
                0 -> {
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
                            .clip(shape = RoundedCornerShape(230.dp))
                            .onFocusChanged { isFocusOnMenu = !it.isFocused }
                            .focusRequester(focusRequester),
                        text = stringResource(id = R.string.autoplay_button),
                        onFocus = { coroutineScope.launch { scrollState.animateScrollTo(0) } },
                        onClick = { navController.navigate(Screen.AutoplayMenu.name) })
                }

                1 -> {
                    MainMenuPlaceHolder("Раздел фото")
                }

                2 -> {
                    MainMenuPlaceHolder("Раздел видео")
                }

                else -> {
                    Error("Ууууууууупс")
                }
            }
        }
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }
}