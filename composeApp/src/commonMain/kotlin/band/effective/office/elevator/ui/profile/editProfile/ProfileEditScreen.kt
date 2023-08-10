package band.effective.office.elevator.ui.profile.editProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.models.PhoneMaskTransformation
import band.effective.office.elevator.ui.models.UserDataEditProfile
import band.effective.office.elevator.ui.models.getAllUserDataEditProfile
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ProfileEditScreen(component: ProfileEditComponent){
    val user by component.user.collectAsState()

    val errorMessage = stringResource(MainRes.strings.profile_format_error)

    LaunchedEffect(component){
        component.label.collect{label->
            when(label){
                is ProfileEditStore.Label.ReturnedInProfile ->component.onOutput(ProfileEditComponent.Output.NavigationBack)
                is ProfileEditStore.Label.SavedChange -> component.onOutput(ProfileEditComponent.Output.NavigationBack)
                is ProfileEditStore.Label.Error -> {
                    showToast(errorMessage)
                }
            }
        }
    }
    ProfileEditScreenContent(
        isErrorTelegram = user.isErrorTelegram,
        isErrorPost = user.isErrorPost,
        isErrorName = user.isErrorName,
        isErrorPhone = user.isErrorPhone,
        userName = user.user.userName,
        post = user.user.post,
        telegram = user.user.telegram,
        phoneNumber = user.user.phoneNumber,
        onReturnToProfile = { component.onEvent(ProfileEditStore.Intent.BackInProfileClicked) },
        onSaveChange = { userName,post, phoneNumber,telegram -> component.onEvent(ProfileEditStore.Intent.SaveChangeClicked(userName = userName, post = post, telegram = telegram, phoneNumber = phoneNumber))}
    )
}

@Composable
private fun ProfileEditScreenContent(
    isErrorPhone: Boolean,
    userName: String,
    post: String,
    telegram: String,
    phoneNumber: String,
    onReturnToProfile: () -> Unit,
    onSaveChange: (userName: String, post: String, phoneNumber: String, telegram: String) -> Unit,
    isErrorName: Boolean,
    isErrorPost: Boolean,
    isErrorTelegram: Boolean
) {
    val userNameText = rememberSaveable { mutableStateOf(userName) }
    val phoneNumberText = rememberSaveable { mutableStateOf(phoneNumber) }
    val postText = rememberSaveable { mutableStateOf(post) }
    val telegramText = rememberSaveable { mutableStateOf(telegram) }

    Column (
        modifier = Modifier.fillMaxSize().background(Color.White).padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        ProfileEditHeader(onReturnToProfile)
        LazyColumn(modifier = Modifier.padding(top= 28.dp)){
            items(getAllUserDataEditProfile()){item->
                    when(item){
                    UserDataEditProfile.Phone -> {
                        FieldsItemStyle(
                            item = item,
                            text = phoneNumberText,
                            error =  isErrorPhone,
                            visualTransformation = PhoneMaskTransformation(),
                            keyboardType = KeyboardType.Phone
                        )
                    }
                    UserDataEditProfile.Person -> {
                        FieldsItemStyle(
                            item = item,
                            text = userNameText,
                            error =  isErrorName
                        )
                    }
                    UserDataEditProfile.Post -> {
                        FieldsItemStyle(
                            item = item,
                            text = postText,
                            error =  isErrorPost
                        )
                    }
                    UserDataEditProfile.Telegram ->{
                        FieldsItemStyle(
                            item = item,
                            text = telegramText,
                            error =  isErrorTelegram
                        )
                    }
                }
            }
            item {
                EffectiveButton(
                    onClick = {onSaveChange(
                        userNameText.value,
                        postText.value,
                        phoneNumberText.value,
                        telegramText.value
                    )},
                    buttonText = stringResource(MainRes.strings.save),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp, top = 32.dp)
                    )
                }
            }
        }
    }



@Composable
private fun FieldsItemStyle(
    item: UserDataEditProfile,
    error: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    text: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text
){
    Column(
        modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
    ) {
        Text(
            stringResource(item.title),
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.body1,
            color = Color.Black
        )
        OutlinedTextField(
            value = text.value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { text.value = it},
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            leadingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(horizontal = 16.dp)) {
                    Icon(
                        painter= painterResource(item.icon),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Divider(modifier = Modifier.height(28.dp).width(2.dp).clip(RoundedCornerShape(4.dp)))
                }
                          },
            trailingIcon = {
                IconButton(onClick = {text.value = ""}){
                    Icon(
                        painter = painterResource(MainRes.images.clear_icon),
                        contentDescription = null,
                        )
                }
                           },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = textGrayColor,
                leadingIconColor = textGrayColor,
                trailingIconColor = textGrayColor),
            isError = error,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}


@Composable
fun ProfileEditHeader(onReturnToProfile: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(top = 40.dp)
    ){
        IconButton(onClick = onReturnToProfile){
            Icon(
                painter = painterResource(MainRes.images.back_button),
                contentDescription = null,
                tint = Color.Black
            )
        }
        TitlePage(
            title = stringResource(MainRes.strings.profile_data),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
