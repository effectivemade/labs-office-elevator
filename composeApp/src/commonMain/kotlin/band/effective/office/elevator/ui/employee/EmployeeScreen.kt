package band.effective.office.elevator.ui.employee

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.borderGray
import band.effective.office.elevator.borderGreen
import band.effective.office.elevator.borderPurple
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.textInBorderPurple
import band.effective.office.elevator.theme_light_background
import band.effective.office.elevator.theme_light_onBackground
import band.effective.office.elevator.theme_light_primary_color
import band.effective.office.elevator.theme_light_tertiary_color
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

data class employeeCard(
    val name: StringResource,
    val post: StringResource,
    val state: StringResource,
    val logo: ImageResource
)

@Composable
fun EmployeeScreen(component: EmployeeComponent) {

    val employeesData = EmployeesData.employeesCardData
    val employeesCount = employeesData.count()
    val employeesInOfficeCount = 1
    val userMessageState = remember { mutableStateOf("") }


    Column {
        Box(
            modifier = Modifier
                .background(theme_light_primary_color)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = stringResource(MainRes.strings.employees),
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    color = theme_light_tertiary_color,
                    modifier = Modifier.padding(20.dp, 55.dp, 15.dp, 25.dp)
                )
                TextField(
                    value = userMessageState.value, onValueChange = {
                        userMessageState.value = it
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(20.dp, 10.dp, 20.dp, 5.dp),
                    textStyle = TextStyle(
                        color = theme_light_tertiary_color,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500)
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = theme_light_background
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(MainRes.strings.search_employee),
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
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
                Spacer(modifier = Modifier.height(0.dp))
            }
        }
        Box(
            modifier = Modifier
                .background(theme_light_onBackground)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical =  25.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Row(modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 15.dp).fillMaxWidth()) {
                        Text(
                            text = stringResource(MainRes.strings.employees) + " ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            color = theme_light_tertiary_color
                        )
                        Text(
                            text = "($employeesCount)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = textInBorderPurple
                        )
                        Text(
                            text = stringResource(MainRes.strings.employee_in_office)
                                    + ": $employeesInOfficeCount",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = textInBorderPurple,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                items(employeesData) { employee_Data ->
                    EveryEmployeeCard(emp = employee_Data)

                }

            }
        }
    }
}

@Composable
fun EveryEmployeeCard(emp: employeeCard) {
    var isExpanded by remember { mutableStateOf(false) }
    val stateColorBorder: Color
    val stateColorText: Color
    if (emp.state == MainRes.strings.employee_in_office) {
        stateColorBorder = borderGreen
        stateColorText = borderGreen
    } else {
        if (emp.state == MainRes.strings.employee_soon_in_office) {
            stateColorBorder = borderPurple
            stateColorText = textInBorderPurple
        } else {
            stateColorBorder = borderGray
            stateColorText = textInBorderGray
        }

    }
    if (isExpanded) {
        //Здесь будет навигация на страницу About Employee)

    }

    Surface(shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .animateContentSize()
            .clickable { isExpanded = !isExpanded },
        color = theme_light_primary_color
    ) {
        Row(modifier = Modifier.padding(6.dp, 10.dp)) {
            Image(
                painter = painterResource(emp.logo),
                contentDescription = "Employee logo",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(56.dp)
            )
            Column(modifier = Modifier.padding(15.dp, 0.dp)) {
                Text(
                    text = stringResource(emp.name),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = theme_light_tertiary_color
                )

                Spacer(modifier = Modifier.padding(0.dp, 4.dp))
                Text(
                    text = stringResource(emp.post),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = textInBorderGray
                )
                Spacer(modifier = Modifier.padding(0.dp, 8.dp))
                Button(
                    onClick = { isExpanded = !isExpanded },
                    colors = ButtonDefaults.buttonColors(theme_light_primary_color),
                    modifier = Modifier
                        .border(1.dp, stateColorBorder, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.elevation(0.dp, 2.dp, 0.dp)
                ) {
                    Text(
                        text = "•   " + stringResource(emp.state),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = stateColorText
                    )
                }
            }
        }
    }
}

object EmployeesData {
    val employeesCardData = listOf(
        employeeCard(
            MainRes.strings.employee_1,
            MainRes.strings.employee_post_1,
            MainRes.strings.employee_in_office,
            MainRes.images.logo_default
        ),
        employeeCard(
            MainRes.strings.employee_2,
            MainRes.strings.employee_post_2,
            MainRes.strings.employee_soon_in_office,
            MainRes.images.logo_default
        ),
        employeeCard(
            MainRes.strings.employee_3,
            MainRes.strings.employee_post_3,
            MainRes.strings.employee_not_soon_in_office,
            MainRes.images.logo_default
        )
    )
}