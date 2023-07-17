package band.effective.office.elevator.ui.authorization.authorization_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.OutlinedTextColorsSetup
import band.effective.office.elevator.components.PrimaryButton
import band.effective.office.elevator.getDefaultFont
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.theme_light_primary_stroke
import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStore
import band.effective.office.elevator.ui.authorization.authorization_profile.store.AuthorizationProfileStore
import band.effective.office.elevator.ui.authorization.components.AuthSubTitle
import band.effective.office.elevator.ui.authorization.components.AuthTabRow
import band.effective.office.elevator.ui.authorization.components.AuthTitle
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AuthorizationProfileScreen(component: AuthorizationProfileComponent) {
    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                AuthorizationProfileStore.Label.AuthorizationProfileFailure -> TODO()
                AuthorizationProfileStore.Label.AuthorizationProfileSuccess -> TODO()
                AuthorizationProfileStore.Label.OpenTGAuthorization -> component.onOutput(
                    AuthorizationProfileComponent.Output.OpenTGScreen
                )

                AuthorizationProfileStore.Label.ReturnInPhoneAuthorization -> component.onOutput(
                    AuthorizationProfileComponent.Output.OpenPhoneScreen
                )
            }
        }
    }

    AuthorizationProfileComponent(onEvent = component::onEvent)
}

@Composable
fun AuthorizationProfileComponent(onEvent: (AuthorizationProfileStore.Intent) -> Unit) {
//    region::Variables
    val isError1 = remember { mutableStateOf(false) }
    val isError2 = remember { mutableStateOf(false) }
    val name = remember { mutableStateOf("") }
    val post = remember { mutableStateOf("") }

    val focusColor1 = remember { mutableStateOf(textGrayColor) }
    val focusColor2 = remember { mutableStateOf(textGrayColor) }

    val closeIcon1 = remember { mutableStateOf(false) }
    val closeIcon2 = remember { mutableStateOf(false) }

    val elevation = ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp,
        hoveredElevation = 0.dp,
        focusedElevation = 0.dp
    )
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
                onEvent(AuthorizationProfileStore.Intent.BackButtonClicked)
            }) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                tint = Color.Black,
                contentDescription = "image_back"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AuthTabRow(1)

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
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

//            region::Input area
//            NAME
            OutlinedTextField(
                value = name.value,
                onValueChange = {
                    name.value = it
                    closeIcon1.value = it.isNotEmpty()
                },
                textStyle = MaterialTheme.typography.body1,
                colors = OutlinedTextColorsSetup(),
                placeholder = {
                    Text(
                        text = stringResource(MainRes.strings.profile_hint),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.8.sp,
                            fontFamily = getDefaultFont(),
                            fontWeight = FontWeight(500),
                            letterSpacing = 0.1.sp,
                            color = textGrayColor
                        )
                    )
                },
                isError = isError1.value,
                singleLine = true,
                trailingIcon = {
                    if (closeIcon1.value) {
                        IconButton(onClick = {
                            name.value = ""
                            closeIcon1.value = false
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "clear_text_field",
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
                        Icon(
                            painterResource(MainRes.images.ic_person),
                            contentDescription = "name_icon"
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Divider(
                            modifier = Modifier
                                .height(28.dp)
                                .width(2.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .padding(vertical = 4.dp)
                                .background(focusColor1.value)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
//                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            focusColor1.value = theme_light_primary_stroke
                        } else {
                            focusColor1.value = textGrayColor
                        }
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

//            POST
            OutlinedTextField(
                value = post.value,
                onValueChange = {
                    post.value = it
                    closeIcon2.value = it.isNotEmpty()
                },
                textStyle = MaterialTheme.typography.body1,
                colors = OutlinedTextColorsSetup(),
                placeholder = {
                    Text(
                        text = stringResource(MainRes.strings.profile_hint_),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.8.sp,
                            fontFamily = getDefaultFont(),
                            fontWeight = FontWeight(500),
                            letterSpacing = 0.1.sp,
                            color = textGrayColor
                        ),
                        lineHeight = 20.8.sp
                    )
                },
                isError = isError2.value,
                singleLine = true,
                trailingIcon = {
                    if (closeIcon2.value) {
                        IconButton(onClick = {
                            post.value = ""
                            closeIcon2.value = false
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "clear_text_field",
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
                        Icon(
                            painterResource(MainRes.images.ic_bag),
                            contentDescription = "post_icon"
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Divider(
                            modifier = Modifier
                                .height(28.dp)
                                .width(2.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .padding(vertical = 4.dp)
                                .background(focusColor2.value)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
//                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            focusColor2.value = theme_light_primary_stroke
                        } else {
                            focusColor2.value = textGrayColor
                        }
                    }
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
                    onEvent(AuthorizationProfileStore.Intent.ContinueButtonClicked)
                }
            )
//            endregion
        }
    }
}