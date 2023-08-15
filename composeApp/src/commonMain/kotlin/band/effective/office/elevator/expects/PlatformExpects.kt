package band.effective.office.elevator.expects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

expect fun showToast(message: String)

expect fun generateVibration(milliseconds: Long)

expect fun makeCall(phoneNumber: String)

expect fun pickTelegram(telegramNick: String)

expect fun pickSBP(phoneNumber: String)