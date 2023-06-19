package band.effective.office.tv.screen.duolingo.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    LazyRow(
        modifier = modifier
            .focusable()
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        userScrollEnabled = true
    ) {
        val usersList: MutableList<List<DuolingoUserUI>> = mutableListOf()
        if (users.size > 5) {
            usersList.add(users.subList(0, 5))
            usersList.add(users.subList(5, users.size))
        }
        else
            usersList.add(users)

        itemsIndexed(usersList) { indexColumn, usersColumn ->
            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(usersColumn) { index: Int, item: DuolingoUserUI ->
                    when (keySort) {
                        KeySortDuolingoUser.Xp -> {
                            GirdItem(
                                name = item.username,
                                indicatorUsers = "${item.totalXp} XP",
                                indicatorUsersColor = Color.Green,
                                photo = item.photo,
                                place = index + 1 + indexColumn*5,
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
                                place = index + 1 + indexColumn*5,
                                flags = item.countryLang
                            )
                        }
                    }
                }
            }
        }
    }
}