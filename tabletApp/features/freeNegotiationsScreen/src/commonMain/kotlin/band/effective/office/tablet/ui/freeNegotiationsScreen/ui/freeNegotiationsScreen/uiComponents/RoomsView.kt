package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun RoomsView(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
                .padding(vertical = 25.dp, horizontal = 15.dp)
        ) {
            for(i in 1..4){
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20))
                        .background(LocalCustomColorsPalette.current.mountainBackground)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}