package band.effective.office.elevator.common.compose.expects

import platform.AudioToolbox.AudioServicesPlaySystemSound
import platform.AudioToolbox.kSystemSoundID_Vibrate
import platform.UIKit.UIAlertView

actual fun showToast(message: String) {
    UIAlertView(null, message, null, "OK", null).show()
}

actual fun generateVibration(milliseconds: Long) {
//    AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
}