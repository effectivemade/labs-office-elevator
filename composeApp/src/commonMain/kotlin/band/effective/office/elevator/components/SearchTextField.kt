package band.effective.office.elevator.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.textInBorderGray
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SearchTextField(
    query: String,
    onQueryUpdate: (String) -> Unit,
    placeholderText: String
) {
    var qurStr by remember { mutableStateOf(query) }
    TextField(
        value = query, onValueChange = {
            qurStr = it
            onQueryUpdate(it)
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 5.dp),
        textStyle = TextStyle(
            color = ExtendedThemeColors.colors.trinidad_400,
            fontSize = 16.sp,
            fontWeight = FontWeight(500)
        ),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = ExtendedThemeColors.colors.transparentColor,
            disabledIndicatorColor = ExtendedThemeColors.colors.transparentColor,
            unfocusedIndicatorColor = ExtendedThemeColors.colors.transparentColor,
            backgroundColor = MaterialTheme.colors.background
        ),
        placeholder = {
            Text(
                text = placeholderText,
                fontSize = 16.sp,
                fontWeight = FontWeight(500),//Style. maththeme
                color = textInBorderGray
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(MainRes.images.baseline_search_24),
                contentDescription = "SearchField",
                tint = textInBorderGray
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(32.dp)
    )
}