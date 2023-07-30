package band.effective.office.elevator.utils.wifiInfo

import band.effective.office.elevator.domain.models.SSID

interface WifiInfoGetter {
    fun getWifiSSID(): SSID
}