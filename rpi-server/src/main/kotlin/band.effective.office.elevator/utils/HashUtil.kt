package band.effective.office.elevator.utils

import java.security.MessageDigest

object HashUtil {

    private val passphrase =
        "J4nf6SHvMBAN6fezbFczETS9yeDgHbYMzW4VAPBp3HFJSJv2ZAb7mPeE7DSHtjL3uAUhd4L6Xmuns3ckq5AvsuU5B27YfznEFvcT2nJ5Hmj268NpWgpnnjFNpRqqTefk3TQrEPrHvXtjsEJ7vEs73QfbPut3d8SZydw3TPR49cWDSdTJ4QKdnxqEKXF6XwBFtn4QHAGTuHCvQAtQWkeNP4z2vMPJ78mz742BgUacrXykbgwKjaRUdSpvznm7XNEj"

    fun sha256(value: String?): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(value.plus(passphrase).toByteArray())
        return bytes.toHex()
    }

    fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }
}