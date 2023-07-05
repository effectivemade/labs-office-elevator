package band.effective.office.elevator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    cornerValue: Dp,
    contentTextSize: TextUnit,
    paddingValues: PaddingValues = PaddingValues(),
    border: BorderStroke? = null,
    elevation: ButtonElevation = ButtonDefaults.elevation(),
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onButtonClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier),
        border = border,
        elevation = elevation,
        shape = RoundedCornerShape(cornerValue),
        colors = colors,
        contentPadding = paddingValues,
        onClick = onButtonClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = contentTextSize,
                letterSpacing = 0.1.sp,
//                fontFamily = FontFamily(Font(R.font.roboto),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
    }
}