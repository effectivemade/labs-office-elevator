package band.effective.office.elevator.ui.employee.allEmployee

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.LoadingIndicator
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.theme_light_onPrimary
import band.effective.office.elevator.ui.employee.allEmployee.models.mappers.EmployeeCard
import band.effective.office.elevator.ui.employee.allEmployee.store.EmployeeStore
import band.effective.office.elevator.utils.generateImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberAsyncImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun EmployeeScreen(component: EmployeeComponent) {

    val employState by component.employState.collectAsState()
    val employeesData = employState.changeShowedEmployeeCards
    val employeesCount = employState.countShowedEmployeeCards
    val userMessageState = employState.query


    LaunchedEffect(component) {
        component.employLabel.collect { label ->
            when (label) {
                is EmployeeStore.Label.ShowProfileScreen -> component.onOutput(EmployeeComponent.Output.OpenProfileScreen(label.employee))
            }
        }
    }
    EmployeeScreenContent(
        isLoading = employState.isLoading,
        employeesData = employeesData,
        employeesCount = employeesCount,
        userMessageState = userMessageState,
        onCardClick = { component.onEvent(EmployeeStore.Intent.OnClickOnEmployee(it)) },
        onTextFieldUpdate = { component.onEvent(EmployeeStore.Intent.OnTextFieldUpdate(it)) })
}

@Composable
fun EmployeeScreenContent(
    employeesData: List<EmployeeCard>,
    employeesCount: String,
    userMessageState: String,
    onCardClick: (String) -> Unit,
    onTextFieldUpdate: (String) -> Unit,
    isLoading: Boolean
) {

    Column {
        Column(
            modifier = Modifier
                .background(theme_light_onPrimary)
                .padding(bottom = 15.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(MainRes.strings.employees),
                fontSize = 20.sp,
                fontWeight = FontWeight(600),//?
                color = ExtendedThemeColors.colors.blackColor,
                modifier = Modifier.padding(start = 20.dp, top = 55.dp, end = 15.dp, bottom = 25.dp)
            )
            TextField(
                value = userMessageState, onValueChange = {
                    onTextFieldUpdate(it)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 5.dp),
                textStyle = TextStyle(
                    color = ExtendedThemeColors.colors.trinidad_400,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = ExtendedThemeColors.colors.transparentColor,
                    disabledIndicatorColor = ExtendedThemeColors.colors.transparentColor,
                    unfocusedIndicatorColor = ExtendedThemeColors.colors.transparentColor,
                    backgroundColor = MaterialTheme.colors.background
                ),
                placeholder = {
                    Text(
                        text = stringResource(MainRes.strings.search_employee),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),//Style. maththeme
                        color = textInBorderGray
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(MainRes.images.baseline_search_24),
                        contentDescription = "SearchField",
                        tint = textInBorderGray
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(32.dp)

            )

        }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
                .padding(start = 20.dp, top = 25.dp, end = 20.dp)
        ) {
            Row(modifier = Modifier.padding(bottom = 25.dp).fillMaxWidth()) {
                Text(
                    text = stringResource(MainRes.strings.employees) + " ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = ExtendedThemeColors.colors.blackColor
                )
                Text(
                    text = "($employeesCount)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = ExtendedThemeColors.colors.purple_heart_800
                )
            }

            when (isLoading){
                true -> {
                    LoadingIndicator()
                }
                false -> {
                    LazyColumn {
                        items(employeesData) { employee_Data ->
                            EveryEmployeeCard(emp = employee_Data, onCardClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable

fun EveryEmployeeCard(emp: EmployeeCard, onCardClick: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    if (isExpanded) {
        onCardClick(emp.id)
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom=15.dp)
            .animateContentSize()
            .clickable { isExpanded = !isExpanded },
        color = MaterialTheme.colors.onPrimary
    ) {
        Row(modifier = Modifier.padding(6.dp, 15.dp)) {

            CompositionLocalProvider(
                LocalImageLoader provides remember {generateImageLoader()},
            ) {
                emp.logoUrl.let { url ->
                    val request = remember(url) {
                        ImageRequest {
                            data(url)
                        }
                    }
                    val painter = rememberAsyncImagePainter(request)
                    Image(
                        painter = painterResource(MainRes.images.logo_default),
                        contentDescription = "Employee logo",
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(56.dp)
                    )

                }
            }
            Column(modifier = Modifier.padding(15.dp, 0.dp)) {
                Text(
                    text = emp.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = ExtendedThemeColors.colors.blackColor
                )

                Spacer(modifier = Modifier.padding(0.dp, 4.dp))
                Text(
                    text = emp.post,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = textInBorderGray
                )
            }
        }
    }
}
