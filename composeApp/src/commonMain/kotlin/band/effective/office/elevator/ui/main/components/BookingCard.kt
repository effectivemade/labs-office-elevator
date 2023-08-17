package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
    onClickCloseOptionMenu: () -> Unit,
    showOptionsMenu:Boolean
)  {
    var expand = remember { mutableStateOf(showOptionsMenu) }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    if(!showOptionsMenu){
        expand.value = false
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .indication(interactionSource, LocalIndication.current)
        .pointerInput(true) {
            detectTapGestures(onPress = {
                expand.value = false
                onClickCloseOptionMenu()
            })
        }
       ){
        Column{
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
                            onClickCloseOptionMenu()
                            expand.value = true
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
        }


        DropDownMenu(
            expanded = expand.value && showOptionsMenu,
            onDismissRequest = {expand.value = false
                               onClickCloseOptionMenu()
                               },
             content = {
                 Column (modifier = Modifier.background(Color.Black)
                   ) {
                     Text("gfg")
                 }
             },
            modifier = Modifier.align(Alignment.BottomEnd)
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
