package band.effective.office.elevator.expects

actual fun setClipboardText(text: String, toastMessage: String) {
    UIPasteboard.general.string = text
    showToast("Text compiled")
}