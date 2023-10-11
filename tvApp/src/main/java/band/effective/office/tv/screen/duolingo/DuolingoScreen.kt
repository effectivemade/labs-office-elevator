package band.effective.office.tv.screen.duolingo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import band.effective.office.tv.R
import band.effective.office.tv.screen.duolingo.components.DuolingoTitle
import band.effective.office.tv.screen.duolingo.components.TopsUser
import band.effective.office.tv.screen.duolingo.model.DuolingoUserUI
import band.effective.office.tv.screen.eventStory.KeySortDuolingoUser

@Composable
fun DuolingoScreen(keySort: KeySortDuolingoUser, duolingoUser: List<DuolingoUserUI>) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 80.dp)
        ) {
            DuolingoTitle(Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.height(10.dp))
            val textTitle = when (keySort){
                KeySortDuolingoUser.Xp -> {
                    stringResource(id = R.string.top_for_XP)
                }
                KeySortDuolingoUser.Streak -> {
                   stringResource(id = R.string.top_for_days)
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