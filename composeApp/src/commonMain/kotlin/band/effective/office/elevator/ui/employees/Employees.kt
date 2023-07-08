package band.effective.office.elevator.ui.employees

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import dev.icerock.moko.resources.compose.painterResource

data class Employee_card(val name:String, val post: String, val state: String, val logo: ImageResource)

@Composable
fun EmployeesScreen(){
    val employees_Data=EmployeesData.employeesCardData
    val employess_Count=employees_Data.count();
    val employeesInOffice_Count=0;
    val userMessageState=remember{ mutableStateOf("") }


    Column{

        Column {
            Text(text="Сотрудники",
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                color = theme_light_tertiary_color,
                modifier = Modifier.padding(20.dp,55.dp,15.dp,25.dp)
            )
            TextField(value=userMessageState.value,onValueChange={
                userMessageState.value=it
            }, modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(20.dp, 10.dp, 20.dp, 5.dp),
                textStyle= TextStyle(
                    color= theme_light_tertiary_color,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500)),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = theme_light_background
                ),
                placeholder = {
                    Text(text="Поиск по сотрудникам",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color= textInBorderGray
                    )},
                leadingIcon = {
                    Icon(painter = painterResource(MainRes.images.baseline_search_24),
                        contentDescription ="SearchField",
                        tint= textInBorderGray)
                },
                singleLine = true,
                shape = MaterialTheme.shapes.large

            )
            Spacer(modifier = Modifier.height(0.dp))
            Text(text = "")
        }

        Box(
            modifier = Modifier
                .background(theme_light_onBackground)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp, 25.dp)
        ){
            LazyColumn {
                item {
                    Row (modifier = Modifier.padding(5.dp,0.dp,0.dp,15.dp).fillMaxWidth()){
                        Text(
                            text = "Сотрудники ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            color = theme_light_tertiary_color
                        )
                        Text(
                            text = "($employess_Count)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = textInBorderPurple
                        )
                        //Spacer(modifier = Modifier.padding(65.dp,0.dp))//65.0
                        Text(
                            text = "В офисе: ${employeesInOffice_Count}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = textInBorderPurple,
                            textAlign = TextAlign.End,
                            modifier = Modifier.size(240.dp,20.dp)
                        )
                    }
                }
                items(employees_Data){employee_Data ->
                    Every_Employee_Card(emp = employee_Data)

                }

            }
        }


    }
}


@Composable
fun Every_Employee_Card(emp:Employee_card){
    var isExpanded by remember { mutableStateOf(false) }
    var state_Color_Border: Color
    var state_Color_Text: Color
    if(emp.state=="В офисе") {
        state_Color_Border = borderGreen
        state_Color_Text = borderGreen
    }
    else{
        if (emp.state=="Будет сегодня"){
            state_Color_Border= borderPurple
            state_Color_Text = textInBorderPurple
        }
        else{
            state_Color_Border= borderGray
            state_Color_Text = textInBorderGray
        }

    }
    if(isExpanded){
        //Здесь будет навигация на страницу About Employee

    }

    Surface(shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .animateContentSize()
            .clickable { isExpanded = !isExpanded } ,
        color = theme_light_primary_color
    ) {
        Row(modifier = Modifier.padding(6.dp,10.dp)){
            Image(painter = painterResource(emp.logo),
                contentDescription = "Employee logo",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(56.dp)
            )
            Column(modifier = Modifier.padding(15.dp,0.dp)){
                Text(text = emp.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = theme_light_tertiary_color
                )

                Spacer(modifier = Modifier.padding(0.dp,4.dp))
                Text(text = emp.post,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = textInBorderGray
                )
                Spacer(modifier = Modifier.padding(0.dp,8.dp))
                Button(onClick = { isExpanded = !isExpanded },
                    colors = ButtonDefaults.buttonColors(theme_light_primary_color),
                    modifier = Modifier
                        .border(1.dp, state_Color_Border, MaterialTheme.shapes.medium),
                    shape = MaterialTheme.shapes.medium) {
                    Text(text="•   "+emp.state,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = state_Color_Text
                    )
                }
            }
        }
    }
}
object EmployeesData{
    val employeesCardData = listOf(
        Employee_card("Иванов Иван",
            "Android-developer",
            "В офисе",
            MainRes.images.logo_default),
        Employee_card("Смирнов Андрей",
            "UI/UX Designer",
            "Будет сегодня",
            MainRes.images.logo_default),
        Employee_card("Васильев Василий",
            "HR",
            "Нет бронирований",
            MainRes.images.logo_default)
    )
}
