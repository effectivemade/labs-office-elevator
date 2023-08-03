package band.effective.office.elevator.ui.booking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.borderPurple
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.textInBorderPurple
import band.effective.office.elevator.ui.booking.components.BookingCard
import band.effective.office.elevator.ui.booking.components.OutlineButtonPurple
import band.effective.office.elevator.ui.models.TypesList
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BookingScreen(bookingComponent: BookingComponent) {
    val list by bookingComponent.state.collectAsState()

    BookingScreenContent()
}

@Composable
private fun BookingScreenContent() {
    Column (modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.onBackground)){
        Box{
            OptionMenu()
            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
                    .graphicsLayer {
                        translationY = 40f
                    }
            ){
                Button(onClick = {},
                    shape = CircleShape,
                    border = BorderStroke(
                        width = 1.dp,
                        color = textGrayColor
                    ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White,
                        contentColor = textGrayColor),
                    modifier = Modifier.size(40.dp)){
                    Image(
                        painter = painterResource(MainRes.images.back_button),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).rotate(90f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        ListBooking()
    }
}

@Composable
private fun ListBooking() {
    Column{
        Row (modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(), verticalAlignment = Alignment.Top) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(MainRes.strings.suitable_options),
                style = MaterialTheme.typography.subtitle1.copy(
                    color = Color.Black,
                    fontWeight = FontWeight(500)
                )
            )
            Spacer(modifier = Modifier.weight(.1f))
            IconButton(onClick = {//TODO: (прописать связь с модалкой Choose zone)
            },  modifier = Modifier.padding(top = 3.dp),
                ){
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        painter = painterResource(MainRes.images.icon_location),
                        tint = textInBorderPurple,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(MainRes.strings.select_zones),
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = borderPurple,
                            fontWeight = FontWeight(400)
                        )
                    )
                }
            }
        }

        LazyColumn (modifier = Modifier.padding(top = 16.dp).padding(horizontal = 16.dp)){
           val listSeats = listOf(
               "Cassipopea | Стол 1",
               "Cassipopea | Стол 2"
           )
            items(listSeats) { seat ->
                BookingCard(
                    seat
                )
            }
        }
    }
}

@Composable
private fun  OptionMenu(){
    Column (modifier = Modifier.clip(
        RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)).background(
        Color.White).padding(top = 48.dp)){
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)){
            TitlePage(
                title = stringResource(MainRes.strings.booking)
            )
            Spacer(modifier = Modifier.weight(.1f))
            OutlineButtonPurple(
                onClick = {},
                icon1 = MainRes.images.icon_map,
                icon2 = MainRes.images.back_button,
                title = MainRes.strings.show_map
            )
        }
        Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp).
        background(MaterialTheme.colors.onBackground)
         ) {
            Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier.padding(vertical = 20.dp),
                    painter = painterResource(MainRes.images.icon_map), //TODO(Заменить карту!")
                    contentDescription = null
                )
            }
        }
        Column(modifier = Modifier.padding(top = 16.dp).padding(horizontal = 16.dp)){
            Text(
                text = stringResource(MainRes.strings.type_booking),
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black
            )
            Row (modifier = Modifier.padding(top = 8.dp).fillMaxWidth(), horizontalArrangement  = Arrangement.Center ){
                val types = listOf(
                    TypesList(name = MainRes.strings.workplace, icon = MainRes.images.table_icon),
                    TypesList(name = MainRes.strings.meeting_room, icon = MainRes.images.icon_meet)
                )
                val selectedType = remember { mutableStateOf(types[0]) }

                types.forEach {type ->
                    val selected = selectedType.value == type
                   Box(
                    modifier = Modifier.padding(
                        end = 12.dp
                    ).border(
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colors.secondary
                    ).background(
                        color = if(selected){MaterialTheme.colors.background}else{Color.White}
                    ).selectable(
                        selected = selected,
                        onClick = {selectedType.value = type}
                    )
                   ){
                       Row (modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp)){
                           Icon(
                               painter = painterResource(type.icon),
                               contentDescription = null,
                               tint = MaterialTheme.colors.secondary
                           )
                           Text(
                               modifier = Modifier.padding(start = 8.dp),
                               text = stringResource(type.name),
                               style = MaterialTheme.typography.body2
                           )
                       }
                   }
                }
            }
            Text(
                modifier = Modifier.padding(top= 12.dp),
                text = stringResource(MainRes.strings.booking_period),
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black
            )
            Row (modifier = Modifier.padding(top = 8.dp, bottom = 24.dp), verticalAlignment = Alignment.CenterVertically){
                IconButton(
                    onClick = {}
                ){
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(MainRes.images.material_calendar_ic),
                        contentDescription = null,
                         tint = MaterialTheme.colors.secondary
                    )
                }
                Text(
                    text = "Пт, 30 июня 12:00 — 14:00", //TODO("Получать текст из календаря")
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
