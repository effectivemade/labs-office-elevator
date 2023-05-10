package band.effective.office.tv.screen.duolingo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.Text
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.screen.duolingo.components.DuolingoTitle
import band.effective.office.tv.screen.duolingo.components.TopsUser
import band.effective.office.tv.screen.eventStory.KeySortDuolingoUser

@Composable
fun DuolingoScreen(keySort: KeySortDuolingoUser, duolingoUser: List<DuolingoUser>) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 80.dp)) {
            DuolingoTitle(Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.height(10.dp))
            val textTitle = when (keySort){
                KeySortDuolingoUser.Xp -> {
                    "Tоп по XP"
                }
                KeySortDuolingoUser.Streak -> {
                    "Топ по дням в ударном режиме"
                }
            }
            Text(
                text = textTitle,
                fontFamily = MaterialTheme.typography.h1.fontFamily,
                fontSize = MaterialTheme.typography.h1.fontSize,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            TopsUser(users = duolingoUser, keySort = keySort)
        }
    }
}