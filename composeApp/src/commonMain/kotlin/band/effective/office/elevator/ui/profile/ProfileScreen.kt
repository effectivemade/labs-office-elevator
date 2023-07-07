package band.effective.office.elevator.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.common.compose.components.GrayText
import band.effective.office.elevator.lightGray
import band.effective.office.elevator.ui.profile.store.ProfileStore
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun ProfileScreen(component: ProfileComponent) {
    val user by component.user.collectAsState()

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                ProfileStore.Label.OnSignedOut -> component.onOutput(ProfileComponent.Output.OpenAuthorizationFlow)
            }
        }
    }

    ProfileScreenContent(
        imageUrl = user.imageUrl,
        username = user.username,
        email = user.email,
        onSignOut = { component.onEvent(ProfileStore.Intent.SignOutClicked) }
    )
}

@Composable
internal fun ProfileScreenContent(
    imageUrl: String?,
    username: String?,
    email: String?,
    onSignOut: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top){
        Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(horizontal = 16.dp).fillMaxWidth()) {
            Text(stringResource(MainRes.strings.profile),
                style = TextStyle(fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.SemiBold))
            Spacer(modifier = Modifier.weight(.1f))
            OutlinedButton(onClick = onSignOut, shape = RoundedCornerShape(size = 8.dp),border = BorderStroke(1.dp,MaterialTheme.colors.secondary), modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(MainRes.images.exit),
                        contentDescription = null,
                        tint =  MaterialTheme.colors.secondary
                    )
                    Text(stringResource(MainRes.strings.exit), style = TextStyle(fontSize = 14.sp, color = Color(0xFFC2410C), fontWeight = FontWeight.Normal), modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

            Box {
                Surface(
                    modifier = Modifier.size(88.dp).align(Alignment.Center),
                    shape = CircleShape,
                    color = lightGray
                ) {
                    Image(
                   modifier = Modifier.fillMaxSize().align(Alignment.Center),
                   painter = painterResource(MainRes.images.job_icon),
                   contentDescription = null,
                )
}
}
Text("Иванов Иван", style = TextStyle(fontSize =15.sp, fontWeight = FontWeight.Medium, color = Color.Black), modifier = Modifier.padding(top = 12.dp))
Text("Android-разработчик", style = TextStyle(fontSize =15.sp, fontWeight = FontWeight.Medium, color = Color(0x80000000)), modifier = Modifier.padding(top = 8.dp))

            var listPrepared by remember { mutableStateOf(false)
            }
            LaunchedEffect(Unit) {
                withContext(Dispatchers.Default) {
                    optionsList.clear()
                    prepareOptionsData()
                    listPrepared = true
                }
            }

            if (listPrepared) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize().padding(top = 24.dp)
                ) {
                    items(optionsList) { item ->
                        OptionsItemStyle(item = item)
                    }
                }
            }
    }
}


@Composable
private fun OptionsItemStyle(item: OptionsData) {
    Row  (verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .padding(horizontal = 16.dp).fillMaxWidth()){
        Icon(
            painter = painterResource(item.icon),
            contentDescription = null,
            tint = Color(0x80000000)
        )
        Text(stringResource(item.title), style = TextStyle(fontSize =15.sp, fontWeight = FontWeight.Normal, color = Color(0x80000000)), modifier = Modifier.padding(start = 12.dp))
        Spacer(modifier = Modifier.weight(.1f))
        Text("+7-999-999-99-99",style = TextStyle(fontSize =15.sp, fontWeight = FontWeight.Normal, color = Color.Black))
        IconButton(onClick = {}){
            Icon(
                painter = painterResource(MainRes.images.next),
                contentDescription = null,
                tint = Color(0x80000000)
            )
        }
    }
    Divider(color = Color(0x80000000), thickness = 1.dp)
}



private val optionsList: ArrayList<OptionsData> = ArrayList()


private fun prepareOptionsData() {

    optionsList.add(
        OptionsData(
            icon = MainRes.images.icon_call,
            title = MainRes.strings.phone_number,
            value = null,
        )
    )

    optionsList.add(
        OptionsData(
            icon = MainRes.images.icon_telegram,
            title = MainRes.strings.telegram,
            value = null,
        )
    )
}
data class OptionsData(val icon: ImageResource, val title: StringResource, val value: StringResource?)
