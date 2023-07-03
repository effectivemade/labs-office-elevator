package band.effective.office.tablet.ui.mainScreen.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun BookingRoomView(onSelectOtherRoom: () -> Unit){
    Button(onClick = { onSelectOtherRoom() }) {
        Text(text = "SelectOtherRoom")
    }
}