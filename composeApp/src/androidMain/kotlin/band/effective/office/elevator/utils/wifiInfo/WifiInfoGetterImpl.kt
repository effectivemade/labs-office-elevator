package band.effective.office.elevator.utils.wifiInfo

import android.content.Context
import android.net.wifi.WifiManager
import band.effective.office.elevator.domain.models.SSID

actual class WifiInfoGetterImpl(
    private val context: Context
) : WifiInfoGetter{
    override fun getWifiSSID(): SSID {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

        // it is deprecated in API 31.
        // https://developer.android.com/reference/android/net/wifi/WifiManager
        // TODO(Artem Gruzdev) rewrite it with fresh code, or leave it like this
        val wifiInfo = wifiManager.connectionInfo
        return SSID(
            ssid = wifiInfo.ssid
        )
    }

}