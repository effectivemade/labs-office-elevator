package band.effective.office.elevator.common.compose.expects

import android.widget.Toast
import band.effective.office.elevator.common.compose.Android

actual fun showToast(message: String) {
    Toast.makeText(Android.context.applicationContext, message, Toast.LENGTH_LONG).show()
}