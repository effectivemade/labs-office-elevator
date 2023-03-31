package band.effective.office.tv.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.list.TvLazyRow
import com.example.effecticetv.ui.theme.CaptionColor

@Composable
fun MenuScreen(modifier: Modifier){
    Box(modifier.fillMaxSize()){
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.width(20.dp))
            for (i in 1..3){
                Box(
                    modifier = Modifier
                        .background(CaptionColor)
                        .fillMaxHeight(0.6f)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "text $i",
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }

        }
    }
}