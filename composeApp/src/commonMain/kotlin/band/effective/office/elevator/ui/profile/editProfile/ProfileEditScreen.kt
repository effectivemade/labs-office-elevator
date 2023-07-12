package band.effective.office.elevator.ui.profile.editProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ProfileEditScreen(component: ProfileEditComponent){
    val user by component.user.collectAsState()

    LaunchedEffect(component){
        component.label.collect{label->
            when(label){
                ProfileEditStore.Label.ReturnedInProfile ->component.onOutput(ProfileEditComponent.Output.OpenProfileFlow)
                else-> {}
            }
        }
    }
    ProfileEditScreenContent(
        username = user.username,
        post = user.post,
        telegram = user.telegram,
        phoneNumber = user.phoneNumber
    ) { component.onEvent(ProfileEditStore.Intent.BackInProfileClicked) }
}

@Composable
private fun ProfileEditScreenContent(
    username: String?,
    post: String?,
    telegram: String?,
    phoneNumber: String?,
    onReturnToProfile: () -> Unit
) {
    Column (
        modifier = Modifier.fillMaxSize().padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        ProfileEditHeader(onReturnToProfile)

        var listPrepared by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(Unit){
            withContext(Dispatchers.Default){
                fieldsList.clear()
                prepareFieldsData(username,post,telegram,phoneNumber)
                listPrepared = true
            }
        }

        if(listPrepared){
            LazyColumn(modifier = Modifier.fillMaxSize().padding(top= 28.dp)){
                items(fieldsList){item->
                    FieldsItemStyle(item = item)
                }
            }
        }
        Spacer(modifier = Modifier.weight(.1f))
        OutlinedButton(
            onClick = {},
            shape = RoundedCornerShape(40.dp),
        ){
            Text(
                text = stringResource(MainRes.strings.save),
                style = TextStyle(
                    fontSize = 16.sp,
                    color =  Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}

@Composable
private fun FieldsItemStyle(item:FieldsData){
    var itemText by remember { mutableStateOf(item.value) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp).fillMaxWidth()
    ) {
            Text(stringResource(item.title), modifier = Modifier.padding(bottom = 8.dp), style = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            )
            )
        itemText?.let { it ->
            OutlinedTextField(
                value = it,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                onValueChange = {itemText = it},
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,

                ),
                leadingIcon = {
                    Icon(
                        painter= painterResource(item.icon),
                        contentDescription = null,
                    )
                   Divider(modifier = Modifier.padding(horizontal = 16.dp).fillMaxHeight(), thickness = 1.dp)
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(MainRes.images.clear_icon),
                        contentDescription = null,
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color(0x80000000),
                    leadingIconColor = Color(0x66000000),
                    trailingIconColor = Color(0x66000000)
                )
            )
        }
    }
}

private fun prepareFieldsData(username: String?, post: String?, telegram: String?, phoneNumber: String?) {

    fieldsList.add(
        FieldsData(
            icon = MainRes.images.person_icon,
            title = MainRes.strings.last_name_and_first_name,
            value = username,
        )
    )
    fieldsList.add(
        FieldsData(
            icon = MainRes.images.symbols_work,
            title = MainRes.strings.post,
            value = post,
        )
    )
    fieldsList.add(
        FieldsData(
            icon = MainRes.images.mask_number,
            title = MainRes.strings.phone_number,
            value = phoneNumber,
        )
    )
    fieldsList.add(
        FieldsData(
            icon = MainRes.images.icon_telegram,
            title = MainRes.strings.telegram,
            value = telegram,
        )
    )
}

private val fieldsList: ArrayList<FieldsData> = ArrayList()
private data class FieldsData(val title:StringResource,val icon:ImageResource,val value: String?)

@Composable
fun ProfileEditHeader(onReturnToProfile: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
    ){
        IconButton(onClick = onReturnToProfile){
            Icon(
                painter = painterResource(MainRes.images.back_button),
                contentDescription = null,
                tint = Color.Black
            )
        }
        Text(
            text = stringResource(MainRes.strings.profile_data),
            style = TextStyle(
                fontSize = 20.sp, color = Color.Black,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
