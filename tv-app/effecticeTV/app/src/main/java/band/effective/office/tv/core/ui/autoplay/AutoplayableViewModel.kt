package band.effective.office.tv.core.ui.autoplay

import kotlinx.coroutines.flow.StateFlow

interface AutoplayableViewModel {
    val state: StateFlow<AutoplayState>
}