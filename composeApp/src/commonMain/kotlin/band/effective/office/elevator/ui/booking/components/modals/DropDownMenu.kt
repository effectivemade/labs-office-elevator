package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BoxScope.DropDownMenu(onClick: (Int) -> Unit) {

    val elevation = ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp,
        hoveredElevation = 0.dp,
        focusedElevation = 0.dp
    )

    val dropDownList =
        listOf(MainRes.strings.week, MainRes.strings.month, MainRes.strings.year)

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 0.dp,
            alignment = Alignment.CenterVertically
        ),
        modifier = Modifier
            .padding(end = 24.dp, bottom = 24.dp)
            .align(alignment = Alignment.BottomEnd)
            .wrapContentSize()
            .clip (shape = RoundedCornerShape(8.dp))
            .background(color = Color.White)
    ) {
        LazyColumn{
            items(dropDownList) { item ->
                with(stringResource(item)) {
                    Button(
                        onClick = {
                            onClick(dropDownList.indexOf(item))
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        elevation = elevation,
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.3f)
                            .wrapContentHeight(),
                    ) {
                        Text(text = this@with)
                    }
                }
            }
        }
    }
}