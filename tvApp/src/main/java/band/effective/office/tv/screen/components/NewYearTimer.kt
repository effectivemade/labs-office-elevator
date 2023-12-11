package band.effective.office.tv.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import band.effective.office.tv.screen.eventStory.models.NewYearCounter
import band.effective.office.tv.utils.getCorrectDeclension

@Composable
fun NewYearTimer(counter: NewYearCounter) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "У вас есть",
            color = Color.Black,
            fontSize = 40.sp
        )
        Text(
            text = "${counter.day} ${
                getCorrectDeclension(
                    counter.day,
                    "день",
                    "дня",
                    "дней"
                )
            } ${counter.hour} ${
                getCorrectDeclension(
                    counter.hour,
                    "час",
                    "часа",
                    "часов"
                )
            } ${counter.min} ${
                getCorrectDeclension(
                    counter.min,
                    "минута",
                    "минуты",
                    "минут"
                )
            } ${counter.sec} ${
                getCorrectDeclension(
                    counter.sec,
                    "секунда",
                    "секунды",
                    "секунд"
                )
            },",
            color = Color.Black,
            fontSize = 40.sp
        )
        Text(
            text = "чтобы приготовить оливьешку",
            color = Color.Black,
            fontSize = 40.sp
        )
    }
}