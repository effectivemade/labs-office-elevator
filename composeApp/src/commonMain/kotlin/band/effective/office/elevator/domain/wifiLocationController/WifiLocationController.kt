package band.effective.office.elevator.domain.wifiLocationController

import band.effective.office.elevator.domain.LocationRepository
import band.effective.office.elevator.domain.models.SSID
import band.effective.office.elevator.utils.wifiInfo.WifiInfoGetter


class WifiLocationController(
    private val locationRepository: LocationRepository,
    private val wifiInfoGetter: WifiInfoGetter
) {
    private val ssid = SSID("effective wifi") //TODO: replace this to backend request or local variable

    suspend fun updateInfoAboutWifiConnection() {
        val currentSSID = wifiInfoGetter.getWifiSSID()
        if (currentSSID == ssid) {
            locationRepository.sendWifiSSID(currentSSID)
        }
    }
}