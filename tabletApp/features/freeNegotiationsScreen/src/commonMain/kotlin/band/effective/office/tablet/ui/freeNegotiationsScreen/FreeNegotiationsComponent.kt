package band.effective.office.tablet.ui.freeNegotiationsScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import band.effective.office.tablet.ui.freeNegotiationsScreen.models.RoomCharacteristicsItem
import band.effective.office.tablet.ui.freeNegotiationsScreen.models.RoomItem
import com.arkivanov.decompose.ComponentContext


class FreeNegotiationsComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {
    val rooms: List<RoomItem> = listOf(
        RoomItem(
            name = "Moon",
            stuff = listOf(
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5 человек"
                ),
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5 человек"
                ),
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5 человек"
                ),
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5 человек"
                ),
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5 человек"
                )
            )
        ),
        RoomItem(
            name = "Moon",
            stuff = listOf(
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5 человек"
                )
            )
        ),
        RoomItem(
            name = "Moon",
            stuff = listOf(
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5 человек"
                )
            )
        ),
    )

}