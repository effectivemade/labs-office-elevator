package band.effective.office.tablet.ui.mainScreen.roomInfoComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.utils.CalendarStringConverter
import java.util.Calendar

@Composable
fun RoomEventListComponent(modifier: Modifier = Modifier, eventsList: List<EventInfo>) {
    Column(modifier = modifier) {
        Text(
            text = "Занятое время",
            fontSize = 17.sp,
            color = Color(0xFF808080)
        )
        Row {
            val lazyListState: LazyListState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.fillMaxWidth().simpleVerticalScrollbar(lazyListState),
                state = lazyListState
            ) {
                items(eventsList) { event ->
                    Spacer(modifier = Modifier.height(30.dp))
                    Row {
                        Text(
                            text = "${event.startTime.time()} - ${event.finishTime.time()}",
                            fontSize = 18.sp,
                            color = Color(0xFFFAFAFA)
                        )
                        Text(
                            text = " · ${event.organizer}",
                            fontSize = 18.sp,
                            color = Color(0xFF808080)
                        )
                    }
                }
            }
        }

    }
}

private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")

//NOTE(Maksim Mishenko): scrollBar: https://stackoverflow.com/questions/66341823/jetpack-compose-scrollbars
//drawRoundRect: https://semicolonspace.com/jetpack-compose-canvas-drawroundrect/
@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier {
    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

        val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
        val scrollbarOffsetY = (firstVisibleElementIndex ?: 0) * elementHeight
        val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight
        val cornerRadius = CornerRadius(x = 36.dp.toPx(), y = 36.dp.toPx())

        if (scrollbarHeight != size.height) {
            drawRoundRect(
                color = Color(0xFF4D4D4D),
                topLeft = Offset(this.size.width - width.toPx(), 0f),
                size = Size(width.toPx(), size.height),
                cornerRadius = cornerRadius
            )
            drawRoundRect(
                color = Color(0xFF808080),
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                cornerRadius = cornerRadius
            )
        }
    }
}