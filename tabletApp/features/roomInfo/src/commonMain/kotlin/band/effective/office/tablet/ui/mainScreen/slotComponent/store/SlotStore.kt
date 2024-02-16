package band.effective.office.tablet.ui.mainScreen.slotComponent.store

import band.effective.office.tablet.domain.model.Slot
import com.arkivanov.mvikotlin.core.store.Store
import java.util.Calendar

interface SlotStore : Store<SlotStore.Intent, SlotStore.State, Nothing> {
    sealed interface Intent {
        data class ClickOnSlot(val slot: Slot) : Intent
        data class UpdateRequest(val room: String, val refresh: Boolean = true) : Intent
        data class UpdateDate(val newDate: Calendar) : Intent
    }

    data class State(
        val slots: List<Slot>,
        val openMultiSlots: List<Slot>
    ) {
        companion object {
            val initValue =
                State(slots = listOf(), openMultiSlots = listOf())
        }

        fun Slot.MultiEventSlot.isOpen() = openMultiSlots.contains(this)
    }
}