package band.effective.office.tv.screen.duolingo.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.tv.R

@Composable
fun DuolingoTitle(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = R.drawable.duolingo_logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(30.dp))
        Text(
            text = "Duolingo",
            fontWeight = FontWeight.SemiBold,
            fontSize = MaterialTheme.typography.h3.fontSize,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}