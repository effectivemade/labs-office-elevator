package band.effective.office.elevator.ui.employee.aboutEmployee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import band.effective.office.elevator.ui.employee.aboutEmployee.components.BookingCardUser
import band.effective.office.elevator.ui.employee.aboutEmployee.components.ContactUserUIComponent
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore
import band.effective.office.elevator.ui.models.ReservedSeat
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AboutEmployee (component: AboutEmployeeComponent){
    val state by component.state.collectAsState()

    AboutEmployeeContent(
        imageUrl = state.user.imageUrl,
        userName = state.user.userName,
        post = state.user.post,
        telegram = state.user.telegram,
        email = state.user.email,
        reservedSeats = state.reservedSeats,
        onClickOpenMap = {component.onOutput(AboutEmployeeComponent.Output.OpenMap)},
        onClickOpenPhone = { component.onEvent(AboutEmployeeStore.Intent.TelephoneClicked) },
        onClickOpenTelegram = { component.onEvent(AboutEmployeeStore.Intent.TelegramClicked) },
        onClickOpenSpb = { component.onEvent(AboutEmployeeStore.Intent.TransferMoneyClicked) },
        onClickBack = {component.onOutput(AboutEmployeeComponent.Output.OpenAllEmployee)}
    )
}

@Composable
private fun AboutEmployeeContent(
    imageUrl: String?,
    userName: String?,
    post: String?,
    telegram: String?,
    email: String?,
    reservedSeats: List<ReservedSeat>,
    onClickBack: () -> Unit,
    onClickOpenMap: () ->Unit,
    onClickOpenPhone: () ->Unit,
    onClickOpenTelegram: () ->Unit,
    onClickOpenSpb: () ->Unit
){
    Column(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.background(Color.White).padding(top = 40.dp, bottom = 24.dp).padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Row ( verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onClickBack){
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

            Row(modifier = Modifier.padding(top = 16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                EffectiveOutlinedButton(
                    MainRes.images.icon_call,
                    text = null,
                    onClick = onClickOpenPhone,
                    modifier = Modifier.padding(end = 8.dp)
                )
                EffectiveOutlinedButton(
                    MainRes.images.icon_telegram,
                    text = null,
                    onClick = onClickOpenTelegram,
                    modifier = Modifier.padding(end = 8.dp)
                )
                EffectiveOutlinedButton(
                    MainRes.images.spb_icon,
                    text = MainRes.strings.transfer,
                    onClick = onClickOpenSpb,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        Column(
            modifier = Modifier.background(MaterialTheme.colors.onBackground),
        ) {
            Text(
                text = stringResource(MainRes.strings.upcoming_bookings),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
            )
            LazyColumn (
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(reservedSeats) { seat ->
                    BookingCardUser(
                        seat,
                        onClickOpenMap
                    )
                }
            }
        }
    }
}