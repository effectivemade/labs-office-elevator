package band.effective.office.elevator.ui.authorization

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.components.OutlinedTextInput
import band.effective.office.elevator.components.PrimaryButton
import band.effective.office.elevator.components.auth_components.AuthSubTitle
import band.effective.office.elevator.components.auth_components.AuthTabRow
import band.effective.office.elevator.components.auth_components.AuthTitle

import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SecondAuthScreen(modifier: Modifier) {
    val tabIndex = remember { mutableStateOf(0) }
    val elevation = ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp,
        hoveredElevation = 0.dp,
        focusedElevation = 0.dp
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(all = 16.dp).then(modifier),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(modifier = Modifier.padding(all = 16.dp), onClick = {

        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "image_back")
        }

        AuthTabRow(tabIndex.value)

        Column(modifier = Modifier.fillMaxSize()) {
            when (tabIndex.value) {
                0 -> Phone()
                1 -> Employee()
                2 -> TG()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            text = stringResource(MainRes.strings._continue),
            modifier = Modifier,
            cornerValue = 40.dp,
            contentTextSize = 16.sp,
            paddingValues = PaddingValues(),
            elevation = elevation,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            ),
            border = null,
            onButtonClick = {
                tabIndex.value++
            }
        )
    }
}

@Composable
fun Phone() {
    val error1 = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        AuthTitle(
            text = stringResource(MainRes.strings.input_number),
            modifier = Modifier.padding(bottom = 7.dp),
            textAlign = TextAlign.Start
        )
        AuthSubTitle(
            text = stringResource(MainRes.strings.select_number),
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Start
        )
        OutlinedTextInput(
            hint = stringResource(MainRes.strings.number_hint),
            error = error1.value,
            modifier = Modifier,
            leadingHolder = {
                Text(text = "+7")
            },
            onTextChange = {

            })
    }
}

@Composable
fun Employee() {
    val error1 = remember { mutableStateOf(false) }
    val error2 = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        AuthTitle(
            text = stringResource(MainRes.strings.input_profile),
            modifier = Modifier.padding(bottom = 7.dp),
            textAlign = TextAlign.Start
        )
        AuthSubTitle(
            text = stringResource(MainRes.strings.select_profile),
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Start
        )
        OutlinedTextInput(
            hint = stringResource(MainRes.strings.profile_hint),
            error = error1.value,
            modifier = Modifier,
            leadingHolder = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "person")
            },
            onTextChange = {

            })
        OutlinedTextInput(
            hint = stringResource(MainRes.strings.profile_hint_),
            error = error2.value,
            modifier = Modifier,
            leadingHolder = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "bag")
            },
            onTextChange = {

            })
    }
}

@Composable
fun TG() {
    val error1 = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        AuthTitle(
            text = stringResource(MainRes.strings.input_employee),
            modifier = Modifier.padding(bottom = 7.dp),
            textAlign = TextAlign.Start
        )
        AuthSubTitle(
            text = stringResource(MainRes.strings.select_employee),
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Start
        )
        OutlinedTextInput(
            hint = stringResource(MainRes.strings.employee_hint),
            error = error1.value,
            modifier = Modifier,
            leadingHolder = {
                Icon(imageVector = Icons.Default.Send, contentDescription = "send")
            },
            onTextChange = {

            })
    }
}