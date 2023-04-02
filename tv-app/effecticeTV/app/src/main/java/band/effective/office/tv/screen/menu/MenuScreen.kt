package band.effective.office.tv.screen.menu

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Text
import band.effective.office.tv.screen.menu.component.ButtonAutoplay
import band.effective.office.tv.screen.menu.component.MenuComponent
import band.effective.office.tv.screen.navigation.NavigationModel
import band.effective.office.tv.ui.theme.BackgroundColor
import com.example.effecticetv.ui.theme.robotoFontFamily
import kotlinx.coroutines.launch

@Composable
fun MenuScreen(
    itemsList: List<NavigationModel>,
    navController: NavController = rememberNavController()
) {
    //TODO(Artem Gruzdev) replace text in str res and use theme. Also do scroll

    val scrollState = rememberScrollState()
    val coroutineScope  = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier
            .background(BackgroundColor)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(346.dp))
            Text(
                text = "Not Sure What to Watch?",
                modifier = Modifier.alpha(0.5f),
                fontFamily = robotoFontFamily(),
                fontSize = 72.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(140.dp))
            ButtonAutoplay()
            Spacer(modifier = Modifier.height(70.dp))
            MenuComponent(
                itemsList = itemsList ,
                onNavigate = { navController.navigate(it) },
                modifier = Modifier
                    .onFocusChanged { focusState ->
                        if (focusState.hasFocus) {
                            coroutineScope.launch {
                                scrollState.animateScrollTo(
                                    this@BoxWithConstraints.maxHeight.value.toInt()
                                )
                            }
                        }
                    }
            )
            Spacer(modifier = Modifier
                .height(250.dp)
                .focusable())
        }
    }
}