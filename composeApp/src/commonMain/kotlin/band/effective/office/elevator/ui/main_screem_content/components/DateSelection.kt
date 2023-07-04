package band.effective.office.elevator.ui.main_screem_content.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import io.github.skeptick.libres.compose.painterResource

@Composable
fun DateSelection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        Text(
            text = "",
            fontSize = 15.sp,
        )
        CalendarTitle()
    }
}

@Composable
fun CalendarTitle() {
    Row {
        Image(
            painter = painterResource(MainRes.image.material_calendar_ic),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = MainRes.string.by_date,
            color = MaterialTheme.colors.secondaryVariant,
            fontSize = 15.sp
        )
    }
}