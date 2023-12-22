package band.effective.office.tablet.ui.selectRoomScreen.failureBooking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.buttons.alert.AlertButton
import band.effective.office.tablet.ui.common.CrossButtonView
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.disconnectColor
import band.effective.office.tablet.ui.theme.h7
import io.github.skeptick.libres.compose.painterResource

@Composable
fun FailureSelectRoomView(onDismissRequest: () -> Unit, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.47f)
            .fillMaxHeight(0.67f)
            .clip(RoundedCornerShape(3))
            .background(LocalCustomColorsPalette.current.elevationBackground)
            .padding(top = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CrossButtonView(
            Modifier
                .fillMaxWidth()
                .padding(end = 42.dp),
            onDismissRequest = { onDismissRequest() }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(MainRes.image.failure),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = MainRes.string.failure_text,
            style = MaterialTheme.typography.h4,
            color = disconnectColor
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = MainRes.string.select_other_room, style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(30.dp))
        AlertButton(modifier = Modifier
            .fillMaxWidth(0.72f)
            .height(64.dp),
            onClick = { onClick() }) {
            Text(text = MainRes.string.see_free_room, style = MaterialTheme.typography.h7)
            Image(
                modifier = Modifier,
                painter = painterResource(MainRes.image.arrow_right),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
            )
        }
    }
}