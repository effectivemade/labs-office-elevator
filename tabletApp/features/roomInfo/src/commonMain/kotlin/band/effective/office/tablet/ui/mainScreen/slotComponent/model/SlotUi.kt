package band.effective.office.tablet.ui.mainScreen.slotComponent.model

import band.effective.office.tablet.domain.model.Slot

sealed interface SlotUi {
    val slot: Slot

    data class SimpleSlot(override val slot: Slot) : SlotUi
    data class MultiSlot(override val slot: Slot, val subSlots: List<SlotUi>, val isOpen: Boolean) :
        SlotUi
    data class DeleteSlot(override val slot: Slot) : SlotUi
}


