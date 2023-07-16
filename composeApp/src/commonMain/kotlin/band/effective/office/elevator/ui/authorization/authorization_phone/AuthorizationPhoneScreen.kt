package band.effective.office.elevator.ui.authorization.authorization_phone

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.PrimaryButton
import band.effective.office.elevator.getDefaultFont
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.theme_light_primary_stroke
import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStore
import band.effective.office.elevator.ui.authorization.components.AuthSubTitle
import band.effective.office.elevator.ui.authorization.components.AuthTabRow
import band.effective.office.elevator.ui.authorization.components.AuthTitle
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AuthorizationPhoneScreen(component: AuthorizationPhoneComponent) {

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                AuthorizationPhoneStore.Label.AuthorizationPhoneFailure -> TODO()
                AuthorizationPhoneStore.Label.AuthorizationPhoneSuccess -> TODO()
            }
        }
    }

    AuthorizationPhoneComponent(onEvent = component::onEvent)
}

@Composable
private fun AuthorizationPhoneComponent(onEvent: (AuthorizationPhoneStore.Intent) -> Unit) {
//    region::Variables
    val elevation = ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp,
        hoveredElevation = 0.dp,
        focusedElevation = 0.dp
    )

    val message = remember { mutableStateOf("") }
    val closeIcon = remember { mutableStateOf(false) }

    val strokeColor = theme_light_primary_stroke
    val unfocusedColor = textGrayColor
    val color = remember { mutableStateOf(unfocusedColor) }
    val errorColor =
        remember { mutableStateOf(Color(0xFFFF3B30)) } /* TODO : Add error color to Colors.kt */
    val tintColor = remember { mutableStateOf(unfocusedColor) }

    val isError = remember { mutableStateOf(false) }
//    endregion

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                horizontal = 16.dp,
                vertical = 48.dp
            ),
    ) {
        IconButton(
            modifier = Modifier.size(size = 48.dp),
            onClick = {
                onEvent(AuthorizationPhoneStore.Intent.BackButtonClicked)
            }) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                tint = Color.Black,
                contentDescription = "image_back"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AuthTabRow(0)

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
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

            OutlinedTextField(
                value = message.value,
                onValueChange = {
//            Printed message
                    message.value = it

//            Stroke color
                    color.value = strokeColor

//            Close icon
                    closeIcon.value = it.isNotEmpty()
                    tintColor.value = Color.Black
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color.Black,
                    letterSpacing = 0.1.sp
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (isError.value) errorColor.value else strokeColor,
                    unfocusedBorderColor = if (isError.value) errorColor.value else unfocusedColor
                ),
                placeholder = {
                    Text(
                        text = stringResource(MainRes.strings.number_hint),
                        color = unfocusedColor,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.8.sp,
                            fontFamily = getDefaultFont(),
                            fontWeight = FontWeight(500),
                            color = unfocusedColor,
                            letterSpacing = 0.1.sp,
                        ),
                        lineHeight = 20.8.sp
                    )
                },
                isError = isError.value,
                singleLine = true,
                trailingIcon = {
                    if (closeIcon.value) {
                        IconButton(onClick = {
                            message.value = ""
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "clear_text_field",
                                tint = tintColor.value
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "+7",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.8.sp,
                                fontFamily = getDefaultFont(),
                                fontWeight = FontWeight(500),
                                color = unfocusedColor,
                                letterSpacing = 0.1.sp,
                            )
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        Divider(
                            modifier = Modifier
                                .height(28.dp)
                                .width(2.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .padding(vertical = 4.dp)
                                .background(if (isError.value) errorColor.value else unfocusedColor)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable(
                        enabled = true,
                        onClick = {
                            color.value = strokeColor
                        }
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = stringResource(MainRes.strings._continue),
                cornerValue = 40.dp,
                contentTextSize = 16.sp,
                paddingValues = PaddingValues(all = 10.dp),
                elevation = elevation,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
                border = null,
                onButtonClick = {
                    onEvent(AuthorizationPhoneStore.Intent.ContinueButtonClicked)
                }
            )
        }
    }
}