package band.effective.office.elevator.ui.freeNegotiations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import band.effective.office.elevator.ui.freeNegotiations.models.RoomCharacteristicsItem
import band.effective.office.elevator.ui.freeNegotiations.models.RoomItem
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory


class FreeNegotiationsComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : ComponentContext by componentContext {
    val rooms: List<RoomItem> = listOf(
        RoomItem(
            name = "Moon",
            stuff = listOf(
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5"
                ),
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5"
                ),
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5"
                ),
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5"
                ),
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5"
                )
            )
        ),
        RoomItem(
            name = "Moon",
            stuff = listOf(
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5"
                )
            )
        ),
        RoomItem(
            name = "Moon",
            stuff = listOf(
                RoomCharacteristicsItem(
                    icon = Icons.Default.Person,
                    text = "5"
                )
            )
        ),
    )

}