package band.effective.office.tablet.domain

import band.effective.office.tablet.network.api.WorkApi
import kotlinx.coroutines.flow.update

class MockController(
    val workApi: WorkApi
){
    fun changeBusy(newValue: Boolean){
        workApi.changeBusy(newValue)
    }

    fun changeEventCount(isMany: Boolean){
        workApi.changeEventCount(isMany)
    }

    fun changeHaveTv(newValue: Boolean){
        workApi.mutableRoomInfo.update { it.copy(isHaveTv = newValue) }
    }
}