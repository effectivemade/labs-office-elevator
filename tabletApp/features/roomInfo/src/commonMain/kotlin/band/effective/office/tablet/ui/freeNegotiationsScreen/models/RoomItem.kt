package band.effective.office.tablet.ui.freeNegotiationsScreen.models

import androidx.compose.ui.graphics.vector.ImageVector

class RoomItem(
    val name: String,
    val stuff: List<RoomCharacteristicsItem>
)

class RoomCharacteristicsItem(
    val icon: ImageVector,
    val text: String
)