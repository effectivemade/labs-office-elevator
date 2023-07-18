package band.effective.office.tablet.ui.selectRoomScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.CrossButtonView
import band.effective.office.tablet.ui.theme.CustomDarkColors
import band.effective.office.tablet.ui.theme.header4
import band.effective.office.tablet.ui.theme.header6

@Composable
fun FreeSelectRoomView(component: RealSelectRoomComponent) {
    val shape = RoundedCornerShape(50)
    val isPressed = remember { mutableStateOf(false) }
    val colorButton =  if(isPressed.value) CustomDarkColors.disabledPrimaryButton else CustomDarkColors.pressedPrimaryButton

    Dialog(
    onDismissRequest = { component.close() }
    ) {
        Box(
           modifier = Modifier
               .size(518.dp, 304.dp)
               .clip(RoundedCornerShape(5))
               .background(CustomDarkColors.elevationBackground),
        ) {

           Column(
               modifier = Modifier.matchParentSize(),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Top
           ) {
               Spacer(modifier = Modifier.height(30.dp))
               CrossButtonView(
                   Modifier.width(518.dp),
                   onDismissRequest = { component.close() }
               )
               Spacer(modifier = Modifier.height(30.dp))
               Box(
                   modifier = Modifier.padding(0.dp),
                   contentAlignment = Alignment.CenterStart
               ) {
                   Text(
                       text = MainRes.string.free_select_room,
                       style = header4,
                       color = CustomDarkColors.primaryTextAndIcon
                   )
               }
               Spacer(modifier = Modifier.height(30.dp))
               Button(
                   modifier = Modifier.size(290.dp, 64.dp),
                   colors = ButtonDefaults.buttonColors(colorButton),
                   shape = shape,
                   onClick = {
                       //TODO
                       isPressed.value = !isPressed.value
                       component.close()
                   }
               ) {
                   Box(contentAlignment = Alignment.Center)
                   {
                       Text(
                           text = MainRes.string.free_select_room_button,
                           style = header6,
                           color = CustomDarkColors.primaryTextAndIcon,
                       )
                   }
               }
           }
        }
    }
}
