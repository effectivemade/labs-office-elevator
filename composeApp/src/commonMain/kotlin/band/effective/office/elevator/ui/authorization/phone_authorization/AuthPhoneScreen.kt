package band.effective.office.elevator.ui.authorization.phone_authorization

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.OutlinedTextInput
import band.effective.office.elevator.components.PrimaryButton
import band.effective.office.elevator.components.auth_components.AuthSubTitle
import band.effective.office.elevator.components.auth_components.AuthTabRow
import band.effective.office.elevator.components.auth_components.AuthTitle
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.authorization.phone_authorization.store.AuthPhoneStore
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AuthTab1Screen(component: AuthPhoneComponent) {

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                is AuthPhoneStore.Label.AuthPhoneFailure -> showToast(label.message)
                is AuthPhoneStore.Label.AuthPhoneSuccess -> component.onOutput(
                    AuthPhoneComponent.Output.OpenTab2Screen
                )
            }
        }
    }

    AuthTab1ScreenContent(onEvent = component::onEvent)
}


@Composable
private fun AuthTab1ScreenContent(onEvent: (AuthPhoneStore.Intent) -> Unit) {
    val error = remember { mutableStateOf(false) }
    val tabIndex = remember { mutableStateOf(0) }
    val elevation = ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp,
        hoveredElevation = 0.dp,
        focusedElevation = 0.dp
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(all = 16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(modifier = Modifier.padding(all = 16.dp), onClick = {

        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "image_back")
        }

        AuthTabRow(tabIndex.value)

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
                error = error.value,
                modifier = Modifier,
                leadingHolder = {
                    Text(text = "+7")
                },
                onTextChange = {

                })
        }

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            text = stringResource(MainRes.strings.`continue`),
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
                onEvent(AuthPhoneStore.Intent.ContinueButtonClicked)
            }
        )
    }
}
