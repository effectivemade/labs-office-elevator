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
import androidx.compose.runtime.remember
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
            Text("Профиль", style = TextStyle(fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.SemiBold))
            Spacer(modifier = Modifier.weight(.1f))
            OutlinedButton(onClick = onSignOut, shape = RoundedCornerShape(size = 8.dp),border = BorderStroke(1.dp,MaterialTheme.colors.secondary), modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        //заменить иконку выхода
                        Icons.Default.ExitToApp,
                        contentDescription = null,
                        tint =  MaterialTheme.colors.secondary
                    )
                    Text("Выйти", style = TextStyle(fontSize = 14.sp, color = MaterialTheme.colors.secondary, fontWeight = FontWeight.Normal), modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

            Box {
                Surface(
                    modifier = Modifier.size(88.dp).align(Alignment.Center),
                    shape = CircleShape,
                    color = lightGray
                ) {
/*
Image(
   modifier = Modifier.fillMaxSize().align(Alignment.Center),
   painter = painter,
   contentDescription = null,
)

Image(
   modifier = Modifier.fillMaxSize().align(Alignment.Center),
   painter = painterResource(MainRes.image.profilerder),
   contentDescription = null,
)
*/
}
}
Text("Иванов Иван", style = TextStyle(fontSize =15.sp, fontWeight = FontWeight.Medium, color = Color.Black), modifier = Modifier.padding(top = 12.dp))
Text("Android-разработчик", style = TextStyle(fontSize =15.sp, fontWeight = FontWeight.Medium, color = Color(0x80000000)), modifier = Modifier.padding(top = 8.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White).padding(top=24.dp),
        ) {
            items(2) {
                Row  (verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(horizontal = 16.dp).fillMaxWidth()){
                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = null,
                        tint = Color(0x80000000)
                    )
                    Text("Телефон", style = TextStyle(fontSize =15.sp, fontWeight = FontWeight.Normal, color = Color(0x80000000)), modifier = Modifier.padding(start = 12.dp))
                    Spacer(modifier = Modifier.weight(.1f))
                    Text("+7-999-999-99-99",style = TextStyle(fontSize =15.sp, fontWeight = FontWeight.Normal, color = Color.Black))
                    IconButton(onClick = onSignOut){
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = null,
                            tint = Color(0x80000000)
                        )
                    }
                }
                Divider(color = Color(0x80000000), thickness = 1.dp)
            }
        }
    }
}