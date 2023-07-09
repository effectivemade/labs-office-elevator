package band.effective.office.elevator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.theme_light_primary_stroke
import band.effective.office.elevator.theme_light_tertiary_color


@Composable
fun OutlinedTextInput(
    hint: String,
    error: Boolean,
    modifier: Modifier,
    leadingHolder: @Composable (() -> Unit),
    onTextChange: (String) -> Unit
) {
    val message = remember { mutableStateOf("") }
    val hintText = remember { mutableStateOf(hint) }

    val closeIcon = remember { mutableStateOf(false) }

    val strokeColor = theme_light_primary_stroke
    val unfocusedColor = theme_light_tertiary_color
    val color = remember { mutableStateOf(theme_light_tertiary_color) }
    val errorColor =
        remember { mutableStateOf(Color(0xFFFF3B30)) } /* TODO : Add error color to Colors.kt */
    val tintColor = remember { mutableStateOf(unfocusedColor) }

    val isError = remember { mutableStateOf(error) }

    OutlinedTextField(
        value = message.value,
        onValueChange = {
//            Printed message
            message.value = it

//            Stroke color
            color.value = strokeColor

//            Close icon
            closeIcon.value = it.isNotEmpty()
            tintColor.value = Color.Black

//            Check for hint
            if (it.isNotEmpty()) hintText.value = hint else hintText.value = ""
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight(500),
            color = Color.Black,
            letterSpacing = 0.1.sp
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (isError.value) errorColor.value else strokeColor,
            unfocusedBorderColor = if (isError.value) errorColor.value else unfocusedColor
        ),
        placeholder = {
            Text(
                text = hint, style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.8.sp,
//                fontFamily = FontFamily(Font(R.font.roboto)), TODO : Add fonts to res
                    fontWeight = FontWeight(500),
                    color = unfocusedColor,
                    letterSpacing = 0.1.sp,
                )
            )
        },
        isError = isError.value,
        singleLine = true,
        trailingIcon = {
            if (closeIcon.value) {
                IconButton(onClick = {
                    message.value = ""
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "clear_text_field",
                        tint = tintColor.value
                    )
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        leadingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                leadingHolder()
//                Icon(imageVector = icon, contentDescription = "leading_icon")
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .height(28.dp)
                        .width(2.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .padding(vertical = 4.dp)
                        .background(if (isError.value) errorColor.value else color.value)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = {
                    color.value = strokeColor
                }
            )
            .then(modifier)
    )

    onTextChange(message.value)
}