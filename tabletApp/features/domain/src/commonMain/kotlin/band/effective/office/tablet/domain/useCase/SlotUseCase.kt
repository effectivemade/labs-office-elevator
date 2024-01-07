package band.effective.office.tablet.domain.useCase

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Slot
import java.util.Calendar

class SlotUseCase {
    @RequiresApi(Build.VERSION_CODES.N)
    fun getSlots(
        start: Calendar,
        finish: Calendar,
        minSlotDur: Int,
        events: List<EventInfo>
    ): List<Slot> {
        return events.fold(
            getEmptyMinSlots(
                start = start,
                finish = finish,
                minSlotDur = minSlotDur
            )
        ) { acc, eventInfo -> acc.addEvent(eventInfo) }.mergeEmptySlots()
    }

    private fun getEmptyMinSlots(
        start: Calendar,
        finish: Calendar,
        minSlotDur: Int,
    ): List<Slot> {
        val count = (finish.timeInMillis - start.timeInMillis) / (minSlotDur * 60000)
        return List<Slot>(count.toInt()) { number ->
            val slotStart = (start.clone() as Calendar).apply { add(Calendar.MINUTE, 15 * number) }
            val slotFinish = (slotStart.clone() as Calendar).apply { add(Calendar.MINUTE, 15) }
            Slot.EmptySlot(slotStart, slotFinish)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun List<Slot>.addEvent(eventInfo: EventInfo): List<Slot> {
        val list = this.toMutableList()
        list.removeIf { slot ->
            slot.start > eventInfo.startTime && slot.start < eventInfo.finishTime ||
                    eventInfo.startTime > slot.start && eventInfo.startTime < slot.finish
        }
        val predSlot = list.first { it.finish > eventInfo.startTime }
        val predSlotIndex = list.indexOf(predSlot)
        list.add(predSlotIndex, eventInfo.toSlot())
        return list
    }

    private fun List<Slot>.mergeEmptySlots(): List<Slot> {
        return fold(mutableListOf<Slot>()) { acc, slot ->
            if (acc.isEmpty()) {
                (acc + slot).toMutableList()
            } else {
                val lastSlot = acc.last()
                if (lastSlot is Slot.EmptySlot && slot is Slot.EmptySlot) {
                    (acc.dropLast(1) + Slot.EmptySlot(lastSlot.start, slot.finish)).toMutableList()
                } else {
                    (acc + slot).toMutableList()
                }
            }
        }
    }

    private fun EventInfo.toSlot(): Slot =
        Slot.EventSlot(start = startTime, finish = finishTime, eventInfo = this)
}
