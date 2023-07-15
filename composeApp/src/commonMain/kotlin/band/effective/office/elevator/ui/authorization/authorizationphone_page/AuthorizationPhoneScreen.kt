package band.effective.office.elevator.ui.authorization.authorizationphone_page

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore
import dev.icerock.moko.resources.compose.stringResource

@Composable
private fun AuthorizationPhoneComponent(onEvent: (AuthorizationStore.Intent) -> Unit) {
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

        AuthTabRow(0)

        Column(modifier = Modifier.fillMaxSize()) {

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

            }
        )
    }
}