package band.effective.office.tablet.domain

import band.effective.office.tablet.network.api.WorkApi
import kotlinx.coroutines.flow.update

class MockController(
    val workApi: WorkApi,
    /*var isBusy: Boolean = false,
    var isManyEvent: Boolean = false,
    var isHaveTV: Boolean = false,
    var isBusyTime: Boolean = false*/
){
    fun changeBusy(newValue: Boolean){

    }
}