package tablet.ui.selectRoomScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import tablet.ui.selectRoomScreen.uiComponents.BookingButtonView
import tablet.ui.selectRoomScreen.uiComponents.DateTimeView
import tablet.ui.selectRoomScreen.uiComponents.LengthEventView
import tablet.ui.selectRoomScreen.uiComponents.OrganizerEventView
import tablet.ui.selectRoomScreen.uiComponents.TitleFieldView

@Composable
fun SelectRoomScreen(component: RealSelectRoomComponent){
    val state by component.state.collectAsState()

    when{
        state.isData -> {
            SelectRoomView(component)
        }

        state.isLoad -> {
            /* (Margarita Djinjolia)
            not in design */
        }

        state.isError -> {
            /* (Margarita Djinjolia)
            not in design */
        }
    }

}

@Composable
fun SelectRoomView(
    component: RealSelectRoomComponent
){
    val modifier = Modifier.background(Color(0xFF3A3736))
    val shape = RoundedCornerShape(16)


    Dialog(
        onDismissRequest = {}
    )
    {
        Box(
            modifier = Modifier
                .size(575.dp, 510.dp)
                .clip(RoundedCornerShape(5))
                .background(Color(0xFF302D2C)),
            contentAlignment = Alignment.Center
        ) {
            Column{
                Text(
                    modifier = Modifier.width(415.dp),
                    text = "Занять ${component.booking.nameRoom}?",
                    fontSize = 28.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xFFFAFAFA),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                TitleFieldView(
                    modifier = Modifier.width(415.dp),
                    title = "когда"
                )
                Spacer(modifier = Modifier.height(16.dp))
                DateTimeView(
                    modifier = modifier.height(64.dp).width(415.dp),
                    shape = shape,
                    booking = component.booking
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        TitleFieldView(
                            modifier = Modifier.width(156.dp),
                            title = "на сколько"
                        )
                        LengthEventView(
                            modifier = modifier.height(64.dp).width(156.dp),
                            shape = shape,
                            booking = component.booking
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        TitleFieldView(
                            modifier = Modifier.width(243.dp),
                            title = "организатор"
                        )
                        OrganizerEventView(
                            modifier = modifier.height(64.dp).width(243.dp),
                            shape = shape,
                            booking = component.booking
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                BookingButtonView(
                    modifier = Modifier.height(64.dp).width(415.dp),
                    color = Color(0xFFEF7234),
                    shape = RoundedCornerShape(40),
                    booking = component.booking
                )
            }
        }
    }

}