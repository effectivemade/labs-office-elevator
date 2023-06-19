package band.effective.office.elevator.expects

import band.effective.office.elevator.ui.uiViewController
import io.github.aakira.napier.Napier
import platform.UIKit.UIAlertController

actual fun showToast(message: String) {
    Napier.e { message }
    val controller = UIAlertController(null, null)
    controller.message = message
    controller.showViewController(uiViewController, null)
}

actual fun generateVibration(milliseconds: Long) {
//    AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
}
