package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.PrimaryButton
import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStore
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun BookingPeriod(
    startDate: String,
    startTime: String,
    finishDate: String,
    finishTime: String,
    repeatBooking: String,
    switchChecked: Boolean,
    closeClick: () -> Unit,
    onSwitchChange: (Boolean) -> Unit,
    bookStartDate: () -> Unit,
    bookFinishDate: () -> Unit,
    confirmBooking: () -> Unit,
    bookingRepeat: () -> Unit
) {
    val elevation = ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp,
        hoveredElevation = 0.dp,
        focusedElevation = 0.dp
    )

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .padding(bottom = 16.dp)
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth(fraction = .3f)
                .height(height = 4.dp)
                .background(
                    color = ExtendedTheme.colors.dividerColor,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .padding(bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            IconButton(
                onClick = closeClick,
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "close booking")
            }
            Text(
                text = stringResource(resource = MainRes.strings.booking_period),
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight(500))
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth(fraction = 1.0f)
                .height(height = 1.dp)
                .background(
                    color = ExtendedTheme.colors._66x
                )
        )

        //Booking area
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(all = 16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(size = 24.dp),
                            painter = painterResource(MainRes.images.material_calendar_ic),
                            contentDescription = "calendar"
                        )
                        Text(
                            text = stringResource(MainRes.strings.whole_day_booking),
                            style = MaterialTheme.typography.button.merge(
                                other = TextStyle(
                                    fontWeight = FontWeight(400)
                                )
                            ),
                            modifier = Modifier.wrapContentSize()
                        )
                    }

                    Switch(
                        checked = switchChecked,
                        onCheckedChange = onSwitchChange,
                        colors = SwitchDefaults.colors()
                    )
                }

                //Start booking date
                TimeLine(
                    date = startDate,
                    time = startTime,
                    elevation = elevation,
                    onPick = bookStartDate
                )

                //Finish booking date
                TimeLine(
                    date = finishDate,
                    time = finishTime,
                    elevation = elevation,
                    onPick = bookFinishDate
                )

                //Book period
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                    elevation = elevation,
                    onClick = bookingRepeat
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
                            .padding(vertical = 16.dp)
                    ) {

                        Icon(
                            modifier = Modifier.size(size = 24.dp),
                            imageVector = Icons.Rounded.Repeat,
                            contentDescription = "repeat booking date"
                        )
                        Text(
                            text = repeatBooking,
                            style = MaterialTheme.typography.button.copy(
                                fontWeight = FontWeight(weight = 400),
                                color = Color.Black
                            )
                        )
                    }
                }
            }

            PrimaryButton(
                text = stringResource(MainRes.strings.confirm_booking),
                cornerValue = 40.dp,
                modifier = Modifier.padding(horizontal = 16.dp),
                contentTextSize = 16.sp,
                paddingValues = PaddingValues(all = 10.dp),
                elevation = elevation,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
                border = null,
                onButtonClick = confirmBooking
            )
        }
    }
}