package band.effective.office.elevator.common.compose.expects

import platform.UIKit.UIAlertView

actual fun showToast(message: String) {
    UIAlertView(null, message, null, "OK", null).show()
}