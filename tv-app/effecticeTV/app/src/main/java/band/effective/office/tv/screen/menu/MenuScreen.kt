package band.effective.office.tv.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.effecticetv.ui.theme.CaptionColor

@Composable
fun MenuScreen(modifier: Modifier, itemsList: List<String>, onNavigate: (String) -> Unit) {
    Box(modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            for (item in itemsList) {
                Box(
                    modifier = Modifier
                        .background(CaptionColor)
                        .fillMaxHeight(0.6f)
                        .weight(1f)
                        .clickable { onNavigate(item) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }

        }
    }
}