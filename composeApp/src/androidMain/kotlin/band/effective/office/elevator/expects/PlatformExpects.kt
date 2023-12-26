package band.effective.office.elevator.expects

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import android.content.Intent
import android.net.Uri
import band.effective.office.elevator.AndroidApp
import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo
import com.seiko.imageloader.component.ComponentRegistryBuilder
import com.seiko.imageloader.component.setupDefaultComponents
import io.github.aakira.napier.Napier
import okio.Path
import okio.Path.Companion.toPath

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
    with(AndroidApp.INSTANCE.applicationContext) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        this@with.startActivity(intent)
    }
}

actual fun pickTelegram(telegramNick: String) {
    with(AndroidApp.INSTANCE.applicationContext) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://t.me/$telegramNick")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            `package` = "org.telegram.messenger"
        }
        this@with.startActivity(intent)
    }
}

@SuppressLint("WrongConstant")
actual fun pickSBP(phoneNumber: String, bankInfo: SBPBankInfo) {
    try {
        with(AndroidApp.INSTANCE.applicationContext) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("tel:$phoneNumber")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                bankInfo.packageName?.let {
                    `package` = it
                }
            }
            this@with.startActivity(intent)
        }
    } catch (e: Exception) {
        showToast(e.message.toString())
    }
}

actual fun ComponentRegistryBuilder.setupDefaultComponents() =
    this.setupDefaultComponents(AndroidApp.INSTANCE.applicationContext)

actual fun getImageCacheDirectoryPath(): Path =
    AndroidApp.INSTANCE.applicationContext.cacheDir.absolutePath.toPath()

actual fun isApplicationInstalled(applicationId: String?): Boolean {
    // TODO (Artem Gruzdev): нужно разобраться с пермишинами
    // https://developer.android.com/training/package-visibility

    return applicationId != null

//    with(AndroidApp.INSTANCE.applicationContext) {
//        Napier.d { "list applications: ${packageManager.getInstalledApplications(0)} "}
//        return packageManager.getInstalledApplications(0).find { info -> info.packageName == applicationId } != null
//    }

}

actual fun getApplicationBankId(bankInfo: SBPBankInfo): String? = bankInfo.packageName