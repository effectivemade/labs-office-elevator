package band.effective.office.elevator.common.compose.expects

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import band.effective.office.elevator.common.compose.Android


actual fun showToast(message: String) {
    Toast.makeText(Android.applicationContext.applicationContext, message, Toast.LENGTH_LONG).show()
}

actual fun generateVibration(milliseconds: Long) {
    val vibrator =
        Android.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                milliseconds,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    } else {
        vibrator.vibrate(milliseconds)
    }
}