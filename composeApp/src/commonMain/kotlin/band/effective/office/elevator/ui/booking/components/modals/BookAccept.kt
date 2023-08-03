package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.LocalExtendedColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.borderPurple
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.theme_light_onPrimary
import band.effective.office.elevator.theme_light_tertiary_color
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookAccept(){
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    val booking_place ="Cassipopeia | Table 1"
    val frequency = "Every single day"
    val startTime = "11:00"
    val endTime = "19:00"
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            Column (modifier = Modifier.fillMaxWidth().background(Color.Green)){
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(fraction = .3f)
                        .height(4.dp)
                        .background(
                            color = ExtendedTheme.colors.dividerColor,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(
                            bottom = 8.dp
                        )
                )
                Row (modifier= Modifier.padding(top=10.dp, start = 16.dp, end = 16.dp)){
                    IconButton(
                        onClick = { scope.launch { sheetState.collapse() } },
                        modifier = Modifier
                            .align(Alignment.Top)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Krestik",
                            tint = theme_light_tertiary_color
                        )
                    }
                    Column(modifier=Modifier.padding(horizontal = 5.dp)){
                        Text(
                            text = booking_place,
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 20.sp,
                            fontWeight = FontWeight(600),
                            color = theme_light_tertiary_color,
                            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                        )
                        Text(
                            text = "$frequency $startTime-$endTime",
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = textInBorderGray,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }
                Button(onClick = {scope.launch{sheetState.collapse()}},
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LocalExtendedColors.current.trinidad_600,
                        contentColor = theme_light_onPrimary
                    )){

                    Text(
                        text= stringResource(MainRes.strings.confirm_booking),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500))
                }


            }

        },
        sheetPeekHeight = 0.dp){
        Button(
            onClick = {scope.launch {
                if(sheetState.isCollapsed)
                    sheetState.expand()
                else
                    sheetState.collapse()}},

        ) {

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(MainRes.strings.select_zones),
                style = MaterialTheme.typography.subtitle1.copy(
                    color = borderPurple,
                    fontWeight = FontWeight(400)
                )
            )
        }

    }
}