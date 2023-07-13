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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.freeNegotiationsScreen.MainRes
import band.effective.office.tablet.ui.freeNegotiationsScreen.components.RoomCard
import band.effective.office.tablet.ui.theme.CustomDarkColors


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
    date: String,
    timeStart: String?,
    timeEnd: String?,
    onClick: () -> Unit,
    component: FreeNegotiationsComponent
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomDarkColors.background)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(CustomDarkColors.surface),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            IconButton(onClick = { onClick() }) {
                Row(modifier = Modifier.padding(bottom = 22.dp)) {
                    Icon(
                        imageVector = ImageVector.vectorResource(MainRes.image.arrow_to_left),
                        contentDescription = null,
                        tint = CustomDarkColors.iconAndText
                    )
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.padding(bottom = 22.dp),
                text = "${MainRes.string.occupy} $date ${MainRes.string.with} " +
                        " $timeStart ${MainRes.string.before}  $timeEnd",
                style = MaterialTheme.typography.h6.copy(
                    color = CustomDarkColors.iconAndText,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            FlowRow(
                modifier = Modifier.padding(26.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (room in component.rooms) {
                    RoomCard(
                        roomItem = room,
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(0.32F)
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                }
            }
        }
    }
}