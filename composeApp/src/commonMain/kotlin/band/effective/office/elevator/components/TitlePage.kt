package band.effective.office.elevator.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TitlePage(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier,
        fontSize =  20.sp, //TODO (Gruzdev) поменять на MaterialTheme.typography.h1.fontSize,
        fontFamily = MaterialTheme.typography.h1.fontFamily,
        color = Color.Black // TODO(Gruzdev) поменять на цвет из темы
    )
}