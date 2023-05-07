package band.effective.office.tv.screen.message.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyMessageScreen(modifier: Modifier, uselessFact: String = "") {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uselessFact != "") {
            Text(text = "There are no messages at the moment, but there are facts")
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = uselessFact,
                fontSize = 25.sp
            )
        } else {
            Text(text = "There are no messages at the moment")
        }
    }

}