package band.effective.office.elevator.ui.aboutEmployee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveOutlinedButton
import band.effective.office.elevator.components.InfoAboutUserUIComponent
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.ui.aboutEmployee.components.ContactUserUIComponent
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AboutEmployee (){
    //val state by component.state.collectAsState()

    AboutEmployeeContent(null,"gjfk","gfkg", "gdf","fdf","fhdjk",{})
}

@Composable
private fun AboutEmployeeContent(
    imageUrl: String?,
    userName: String?,
    post: String?,
    telegram: String?,
    phoneNumber: String?,
    email: String?,
    onClick: () ->Unit
){
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(top = 40.dp).padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Row ( verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = {}){
                Icon(
                    painter = painterResource(MainRes.images.back_button),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            TitlePage(
                title = stringResource(MainRes.strings.about_the_employee),
                modifier = Modifier.align(Alignment.CenterVertically).padding(start = 16.dp)
            )
        }

        Row(modifier = Modifier.padding(top = 24.dp)){
            Column {
                InfoAboutUserUIComponent(userName,post)
                ContactUserUIComponent(MainRes.images.icon_telegram, telegram, modifier = Modifier.padding(top = 18.dp))
                ContactUserUIComponent(MainRes.images.mail, email, modifier = Modifier.padding(top = 10.dp))
            }
            Spacer(modifier = Modifier.weight(.1f))
            Surface(
                modifier = Modifier.size(88.dp),
                shape = CircleShape,
                color = Color(0xFFEBE4FF)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(MainRes.images.job_icon),
                    contentScale = ContentScale.Inside,
                    contentDescription = null,
                )
            }
        }

        Row(modifier = Modifier.padding(top = 16.dp), horizontalArrangement = Arrangement.Start){
            EffectiveOutlinedButton(
                MainRes.images.icon_call,
                text = null,
                onClick = onClick,
            )
            EffectiveOutlinedButton(
                MainRes.images.icon_telegram,
                text = null,
                onClick = onClick,
            )
        }
    }
}