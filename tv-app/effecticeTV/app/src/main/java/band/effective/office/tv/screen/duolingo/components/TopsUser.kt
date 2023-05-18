package band.effective.office.tv.screen.duolingo.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tv.screen.duolingo.model.DuolingoUserUI
import band.effective.office.tv.screen.eventStory.KeySortDuolingoUser
import band.effective.office.tv.utils.getCorrectDeclension

@Composable
fun TopsUser(
    modifier: Modifier = Modifier,
    users: List<DuolingoUserUI>,
    keySort: KeySortDuolingoUser
) {
    LazyVerticalGrid(
        modifier = modifier.focusable(),
        columns = GridCells.Fixed(count = 2),
        horizontalArrangement = Arrangement.spacedBy(100.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(users) { index: Int, item: DuolingoUserUI ->
            when (keySort) {
                KeySortDuolingoUser.Xp -> {
                    GirdItem(
                        name = item.username,
                        indicatorUsers = "${item.totalXp} XP",
                        indicatorUsersColor = Color.Green,
                        photo = item.photo,
                        place = index + 1,
                        flags = item.countryLang
                    )
                }
                KeySortDuolingoUser.Streak -> {
                    GirdItem(
                        name = item.username,
                        indicatorUsers = "${item.streakDay} " +
                                getCorrectDeclension(
                                    number = item.streakDay,
                                    "день", "дня", "дней"
                                ),
                        indicatorUsersColor = Color.Red,
                        photo = item.photo,
                        place = index + 1,
                        flags = item.countryLang
                    )
                }
            }
        }
    }
}