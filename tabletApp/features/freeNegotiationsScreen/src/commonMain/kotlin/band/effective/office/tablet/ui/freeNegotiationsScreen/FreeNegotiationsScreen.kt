package band.effective.office.tablet.ui.freeNegotiationsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.ui.freeNegotiationsScreen.components.RoomCard


@Composable
fun FreeNegotiationsScreen(component: FreeNegotiationsComponent) {
    FreeNegotiationsContent(
        date = "5 июля",
        timeStart = "17:49",
        timeEnd = "19:00",
        onClick = {},
        component = component,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun FreeNegotiationsContent(
    date: String?,
    timeStart: String?,
    timeEnd: String?,
    onClick: () -> Unit,
    component: FreeNegotiationsComponent
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF1E1C1A))
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFF252322)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            IconButton(onClick = { onClick() }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color(0xFFFA362F8)
                    )
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Занять $date с $timeStart до $timeEnd",
                style = TextStyle(
                    color = Color(0xFFFA362F8),
                    fontSize = 20.sp
                )
            )
        }
        FlowRow(modifier = Modifier.padding(24.dp)) {
            for (room in component.rooms) {
                RoomCard(
                    roomItem = room,
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(0.3F)
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
    }
}