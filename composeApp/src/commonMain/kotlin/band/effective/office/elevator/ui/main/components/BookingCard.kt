package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.components.DropDownMenu
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.models.ReservedSeat

@Composable
fun BookingCard(
    seat: ReservedSeat,
    modifier: Modifier = Modifier,
    onClickOptionMenu: (Int) -> Unit,
    onClickShowOptions: () -> Unit,
    showOptionsMenu: Boolean,
    onClickCloseOptionMenu: () -> Unit
)  {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current
    Box(modifier = Modifier
        .fillMaxWidth()
        .indication(interactionSource, LocalIndication.current)
        .pointerInput(true) {
            detectTapGestures(onLongPress = {
                isContextMenuVisible = true
                pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
            }, onPress = {
                val press = PressInteraction.Press(it)
                interactionSource.emit(press)
                tryAwaitRelease()
                interactionSource.emit(PressInteraction.Release(press))
            })
        }
       ){
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            SeatIcon()
            Spacer(modifier = Modifier.width(12.dp))
            SeatTitle(seat)
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        onClickShowOptions()
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(80.dp)),
                    colors = IconButtonDefaults.iconButtonColors(),
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "show option menu",
                        modifier = Modifier,
                        tint = MaterialTheme.colors.secondaryVariant
                    )
                }
            }
        }

        DropDownMenu(
            expanded = showOptionsMenu,
            onDismissRequest = onClickCloseOptionMenu,
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            ),
             content = {
                 Column (modifier = Modifier.background(Color.Black)) {
                     Text("gfg")
                 }
             }
        )

    }
}

@Composable
fun SeatTitle(seat: ReservedSeat) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = seat.seatName,
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = seat.bookingDay,
                style = MaterialTheme.typography.subtitle1.copy(color = textGrayColor),
                modifier = Modifier.wrapContentSize()
            )
            Divider(
                modifier = Modifier
                    .width(6.dp)
                    .rotate(90f),
                thickness = 2.dp,
            )
            Text(
                text = seat.bookingTime,
                style = MaterialTheme.typography.subtitle1.copy(color = textGrayColor),
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}
