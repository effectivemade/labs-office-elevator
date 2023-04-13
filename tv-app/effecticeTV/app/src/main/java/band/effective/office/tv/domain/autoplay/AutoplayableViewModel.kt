package band.effective.office.tv.domain.autoplay

import band.effective.office.tv.domain.autoplay.model.AutoplayState
import kotlinx.coroutines.flow.StateFlow

interface AutoplayableViewModel {
    val state: StateFlow<AutoplayState>

    fun switchToFirstItem()
    fun switchToLastItem()
}