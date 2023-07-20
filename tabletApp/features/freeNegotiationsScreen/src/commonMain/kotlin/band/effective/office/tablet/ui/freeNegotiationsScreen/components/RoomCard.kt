package band.effective.office.tablet.ui.freeNegotiationsScreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.freeNegotiationsScreen.MainRes
import band.effective.office.tablet.ui.freeNegotiationsScreen.models.RoomItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomCard(
    roomItem: RoomItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth().height(288.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Text(
                    text = roomItem.name,
                    style = MaterialTheme.typography.h2.copy(
                        Color.White,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(28.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 28.dp),
                        maxItemsInEachRow = 2
                    ) {
                        for (stuff in roomItem.stuff) {
                            RoomCharacteristics(
                                icon = stuff.icon,
                                text = stuff.text
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .border(2.dp, shape = RoundedCornerShape(40.dp), color = MaterialTheme.colors.primary)
                .fillMaxWidth().height(64.dp),
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background
            )
        ) {
            Text(
                text = "${MainRes.string.occupy} ${roomItem.name}",
                style = MaterialTheme.typography.subtitle1.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}