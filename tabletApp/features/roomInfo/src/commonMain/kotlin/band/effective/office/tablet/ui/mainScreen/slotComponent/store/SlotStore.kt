package band.effective.office.tablet.ui.mainScreen.slotComponent.store

import band.effective.office.tablet.domain.model.Slot
import com.arkivanov.mvikotlin.core.store.Store

interface SlotStore : Store<SlotStore.Intent, SlotStore.State, Nothing> {
    sealed interface Intent {
        data class ClickOnSlot(val slot: Slot) : Intent
    }

    data class State(
        val slots: List<Slot>
    ) {
        companion object {
            val initValue = State(slots = listOf())
        }
    }
}