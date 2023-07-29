package band.effective.office.elevator.expects

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
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

actual fun makeCall(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(AndroidApp.INSTANCE.applicationContext, intent, Bundle())
}

actual fun pickTelegram(telegramNick: String) {

    try {
        val telegramUri = Uri.parse("https://t.me/$telegramNick")
        val intent = Intent(Intent.ACTION_VIEW, telegramUri)
        intent.`package` = "org.telegram.messenger"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

//    if (intent.resolveActivity(AndroidApp.INSTANCE.applicationContext.packageManager) != null) {
        startActivity(AndroidApp.INSTANCE.applicationContext, intent, Bundle())
    } catch (e: Exception) {
        showToast("Telegram app is not installed")
    }
//    }


//    val intent = Intent(Intent.ACTION_VIEW).apply {
//        data = Uri.parse("https://telegram.me/$telegramNick")
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK
//    }
//    startActivity(AndroidApp.INSTANCE.applicationContext, intent, Bundle())
}

actual fun pickSBP(phoneNumber: String) {
//    val packageName = "ru.sberbankmobile"
//
//    try {
//        val intent = AndroidApp.INSTANCE.applicationContext.packageManager.getLaunchIntentForPackage(packageName)
//        if (intent != null) {
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            AndroidApp.INSTANCE.applicationContext.startActivity(intent)
//        } else {
//            if (intent != null) {
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
//                AndroidApp.INSTANCE.applicationContext.startActivity(playStoreIntent)
//            }
//        }
//    } catch (e: ActivityNotFoundException) {
//        val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
//        AndroidApp.INSTANCE.applicationContext.startActivity(playStoreIntent)
//    }

    val url =
        "https://web4-new.online.sberbank.ru/payments/fps"
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
//        `package` = "ru.sberbankmobile"
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    AndroidApp.INSTANCE.applicationContext.startActivity(intent)

}