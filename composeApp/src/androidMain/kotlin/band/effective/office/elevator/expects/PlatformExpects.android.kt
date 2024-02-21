package band.effective.office.elevator.expects

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import band.effective.office.elevator.AndroidApp


actual fun setClipboardText(text: String, toastMessage: String) {
    val context = AndroidApp.INSTANCE

    val clip = ClipData.newPlainText(toastMessage, text)
    val clipboardService =  context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardService.setPrimaryClip(clip)
    showToast("$toastMessage compiled")
}