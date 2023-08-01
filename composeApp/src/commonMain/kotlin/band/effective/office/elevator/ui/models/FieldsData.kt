package band.effective.office.elevator.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

data class FieldsData(
    val title:StringResource,
    val icon:ImageResource,
    val value: MutableState<String>,
    val isError: Boolean = false,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val keyboardType: KeyboardType = KeyboardType.Text,
    )