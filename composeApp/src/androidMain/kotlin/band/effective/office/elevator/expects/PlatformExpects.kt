package band.effective.office.elevator.expects

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
        startActivity(AndroidApp.INSTANCE.applicationContext, intent, Bundle())
    } catch (e: Exception) {
        showToast("Telegram app is not installed")
    }
}

@SuppressLint("WrongConstant")
actual fun pickSBP(phoneNumber: String) {


    try {
        val intent = Intent().apply {
            flags = 0x24000000 or Intent.FLAG_ACTIVITY_NEW_TASK
            component = ComponentName(
                "ru.sberbankmobile",
//            "ru.sberbank.mobile.auth.presentation.prelogin.PreloginActivity"
//            "ru.sberbank.mobile.app.configuration.SbolApplication"
                "ru.sberbank.mobile.auth.presentation.splash.SplashActivity"
            )
        }
        AndroidApp.INSTANCE.applicationContext.startActivity(intent)
    } catch (e: Exception) {
        showToast("SberBank app is not installed")
        val url =
            "https://web4-new.online.sberbank.ru/payments/fps"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        AndroidApp.INSTANCE.applicationContext.startActivity(intent)
    }

//    region::V4
//    val packageName = "ru.sberbankmobile"
//    val activityName =
//        "ru.sberbank.mobile.feature.efs.transfers.quickpaymentssystem.impl.MainActivity"
//
//    val intent = Intent()
//    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//    intent.component = ComponentName(packageName, activityName)
//    startActivity(AndroidApp.INSTANCE.applicationContext, intent, Bundle())
//    endregion

//    region::V3

//    val packageName = "ru.sberbankmobile/ru.sberbank.mobile.feature.efs.transfers.quickpaymentssystem.impl.TransferActivity"
//
//    with(AndroidApp.INSTANCE.applicationContext) {
//        try {
//            val intent: Intent? = this@with.packageManager.getLaunchIntentForPackage(packageName)
////            val intent = Intent(Intent.ACTION_VIEW)
////            intent.`package` = "ru.sberbankmobile"
////        intent.data = Uri.parse("https://web4-new.online.sberbank.ru/payments/fps")
//            if (intent != null) {
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                this@with.startActivity(intent)
//            } else {
//                showToast("Sberbank app is not installed")
//            }
//        } catch (e: Exception) {
//        }
//    }
//    endregion

//    region::V1
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
//    endregion

//    region:: V2
//    val url =
//        "https://web4-new.online.sberbank.ru/payments/fps"
//    val intent = Intent(Intent.ACTION_VIEW).apply {
//        data = Uri.parse(url)
////        `package` = "ru.sberbankmobile"
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK
//    }
//    AndroidApp.INSTANCE.applicationContext.startActivity(intent)
//    endregion

}