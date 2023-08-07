package band.effective.office.elevator.ui.models

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

data class  FieldsDataForProfile(
    val title: StringResource,
    val icon: ImageResource,
    val value: String
)