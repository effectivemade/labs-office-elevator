package band.effective.office.tv.screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R

@Composable
fun NoStoriesScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.today_no_stories), fontSize = 26.sp, color = Color.White)
            Button(
                modifier = Modifier.padding(top = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = Color.White
                ),
                onClick = { /*TODO (Pakhomenko Egor): Add logic after merge with whole app*/ }) {
                Text(
                    fontSize = 16.sp,
                    text = stringResource(id = R.string.today_no_stories),
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}