package band.effective.office.elevator.domain

import band.effective.office.elevator.domain.models.SSID

interface LocationRepository {
    suspend fun sendWifiSSID(ssid: SSID)
}