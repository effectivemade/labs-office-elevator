package band.effective.office.tv.screen.duolingo.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.tv.R
import band.effective.office.tv.screen.duolingo.model.FlagInfo
import coil.compose.AsyncImage

@Composable
fun GirdItem(
    modifier: Modifier = Modifier,
    name: String,
    indicatorUsers: String,
    indicatorUsersColor: Color,
    flags: List<FlagInfo>,
    photo: String,
    place: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.width(270.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = place.toString(),
                fontStyle = MaterialTheme.typography.h2.fontStyle,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.h2.fontSize,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = photo,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.duolingo_logo),
                    error = painterResource(id = R.drawable.duolingo_logo)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(
                        modifier = Modifier.width(190.dp),
                        text = name,
                        fontStyle = MaterialTheme.typography.h2.fontStyle,
                        fontSize = MaterialTheme.typography.h2.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        maxLines = 1
                    )
                    Row {
                        flags.forEach { flag ->
                            Flag(modifier = Modifier.size(15.dp), drawableFlagId = flag.drawableId)
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
            }

        }
        Text(
            text = indicatorUsers,
            fontStyle = MaterialTheme.typography.h2.fontStyle,
            fontSize = MaterialTheme.typography.h2.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = indicatorUsersColor,
            textAlign = TextAlign.Right
        )
    }
}