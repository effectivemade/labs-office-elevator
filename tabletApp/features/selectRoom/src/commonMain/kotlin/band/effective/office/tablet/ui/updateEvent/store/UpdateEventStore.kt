package band.effective.office.tablet.ui.updateEvent.store

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Organizer
import com.arkivanov.mvikotlin.core.store.Store
import java.util.Calendar
import java.util.GregorianCalendar

interface UpdateEventStore : Store<UpdateEventStore.Intent, UpdateEventStore.State, UpdateEventStore.Label> {
    sealed interface Intent {
        data class OnUpdateLength(val update: Int) : Intent
        data class OnUpdateDate(val updateInDays: Int) : Intent
        object OnExpandedChange : Intent
        object OnSelectOrganizer : Intent
        object OnUpdateEvent : Intent
        object OnDeleteEvent : Intent
        data class OnInit(val event: EventInfo) : Intent
    }

    data class State(
        val duration: Int,
        val date: Calendar,
        val organizers: List<Organizer>,
        val selectOrganizer: Organizer,
        val expanded: Boolean,
        val event: EventInfo,
        val isLoad: Boolean,
        val isError: Boolean
    ) {
        companion object {
            val defaultValue = State(
                duration = 30,
                date = GregorianCalendar(),
                organizers = listOf(),
                selectOrganizer = Organizer.default,
                expanded = false,
                event = EventInfo.emptyEvent,
                isLoad = false,
                isError = false
            )
        }
    }

    sealed interface Label {
        object Close : Label
    }
}