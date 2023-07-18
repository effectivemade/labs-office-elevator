package band.effective.office.tablet.ui.selectRoomScreen.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.ui.theme.CustomDarkColors
import band.effective.office.tablet.ui.theme.CustomLightColors
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h6_button
import band.effective.office.tablet.utils.time24

@Composable
fun BookingButtonView(modifier: Modifier, shape: RoundedCornerShape, text: String, onClick:() -> Unit) {
    val isPressed = remember { mutableStateOf(false) }
    val colorButton =  if(isPressed.value)
        LocalCustomColorsPalette.current.pressedPrimaryButton else MaterialTheme.colors.primary

    Button(
        modifier = modifier,
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(colorButton),
        shape = shape,
        onClick = {
            isPressed.value = !isPressed.value
            onClick()
        }
    ) {
        Box(contentAlignment = Alignment.Center)
        {
            Text(
                text = text,
                style = MaterialTheme.typography.h6_button,
                color = when(LocalCustomColorsPalette.current) {
                    CustomDarkColors -> LocalCustomColorsPalette.current.primaryTextAndIcon
                    else -> MaterialTheme.colors.background
                },
              //  letterSpacing = 0.1.sp
            )
        }
    }
}