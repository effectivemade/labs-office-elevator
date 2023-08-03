package band.effective.office.elevator.components

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.theme_light_primary_stroke

@Composable
fun OutlinedTextColorsSetup() = TextFieldDefaults.outlinedTextFieldColors(
//                    region::Border
    focusedBorderColor = theme_light_primary_stroke,
    unfocusedBorderColor = textGrayColor,
    disabledBorderColor = textGrayColor,
    errorBorderColor = ExtendedThemeColors.colors.error,
//                    endregion

//                    region::Trailing icon
    trailingIconColor = Color.Black,
    disabledTrailingIconColor = textGrayColor,
    errorTrailingIconColor = Color.Black,
//                    endregion

//                    region::Leading icon
    disabledLeadingIconColor = textGrayColor,
//                    endregion

//                    region::Cursor colors
    cursorColor = theme_light_primary_stroke,
    errorCursorColor = ExtendedThemeColors.colors.error
//                    endregion
)