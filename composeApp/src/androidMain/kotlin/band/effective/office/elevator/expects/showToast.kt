package band.effective.office.elevator.expects

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import band.effective.office.elevator.AndroidApp

actual fun showToast(message: String) {
    Toast.makeText(AndroidApp.INSTANCE.applicationContext, message, Toast.LENGTH_SHORT).show()
}

actual fun generateVibration(milliseconds: Long) {
    val vibrator =
        AndroidApp.INSTANCE.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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
