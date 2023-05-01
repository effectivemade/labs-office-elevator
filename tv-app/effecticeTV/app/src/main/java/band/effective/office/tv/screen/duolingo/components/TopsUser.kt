package band.effective.office.tv.screen.duolingo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tv.domain.model.duolingo.DuolingoUser

@Composable
fun TopsUser(modifier: Modifier = Modifier, users: List<DuolingoUser>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        horizontalArrangement = Arrangement.spacedBy(200.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ){
        itemsIndexed(users){index: Int, item: DuolingoUser ->
            GirdItem(
                name = item.username,
                indicatorUsers = "${item.totalXp} XP",
                indicatorUsersColor = Color.Gray,
                photo = item.photo,
                place = index + 1
            )
        }
    }
}