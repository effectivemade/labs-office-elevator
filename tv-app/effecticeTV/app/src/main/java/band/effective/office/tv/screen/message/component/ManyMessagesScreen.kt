package band.effective.office.tv.screen.message.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tv.domain.model.message.BotMessage

@Composable
fun ManyMessagesScreen(
    modifier: Modifier,
    messagesList: List<BotMessage>,
    currentIndex: Int = 0,
    textColor: Color = Color.Black,
    onClickButton: (() -> Unit)? = null
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            ProgressIndicator(
                modifier = Modifier,
                elementModifier = Modifier.clip(CircleShape),
                count = messagesList.size,
                currentIndex = currentIndex
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            OneMessageScreen(
                modifier = modifier,
                message = messagesList[currentIndex],
                onClickBackButton = onClickButton,
                textColor = textColor
            )
        }
    }
}


// TODO(Maksim Mishenko): delete after merge
@Composable
fun ProgressIndicator(
    modifier: Modifier,
    elementModifier: Modifier,
    count: Int,
    currentIndex: Int,
    elementColor: Color = MaterialTheme.colors.secondary,
    currentElementColor: Color = Color.White
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        for (index in 0 until count) {
            val animatedColor =
                animateColorAsState(
                    if (index == currentIndex) currentElementColor else elementColor
                )
            Row {
                Box(
                    modifier = elementModifier
                        .width(8.dp)
                        .height(8.dp)
                        .background(animatedColor.value)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}