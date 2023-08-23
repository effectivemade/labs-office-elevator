package band.effective.office.tablet.ui.updateEvent.store

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Organizer
import com.arkivanov.mvikotlin.core.store.Store
import java.util.Calendar
import java.util.GregorianCalendar

interface UpdateEventStore :
    Store<UpdateEventStore.Intent, UpdateEventStore.State, UpdateEventStore.Label> {
    sealed interface Intent {
        data class OnUpdateLength(val update: Int) : Intent
        data class OnUpdateDate(val updateInDays: Int) : Intent
        data class OnSetDate(
            val year: Int,
            val month: Int,
            val day: Int,
            val hour: Int,
            val minute: Int
        ) : Intent

        object OnExpandedChange : Intent
        data class OnSelectOrganizer(val newOrganizer: Organizer) : Intent
        object OnUpdateEvent : Intent
        object OnDeleteEvent : Intent
        data class OnInit(val event: EventInfo) : Intent
        data class OnInput(val input: String) : Intent
        object OnDoneInput : Intent
        object OnOpenSelectDateDialog : Intent
        object OnCloseSelectDateDialog : Intent
    }

    data class State(
        val duration: Int,
        val date: Calendar,
        val organizers: List<Organizer>,
        val selectOrganizers: List<Organizer>,
        val selectOrganizer: Organizer,
        val expanded: Boolean,
        val event: EventInfo,
        val isLoadUpdate: Boolean,
        val isErrorUpdate: Boolean,
        val inputText: String,
        val isLoadDelete: Boolean,
        val isErrorDelete: Boolean,
        val showSelectDate: Boolean,
        val enableUpdateButton: Boolean
    ) {
        companion object {
            val defaultValue = State(
                duration = 30,
                date = GregorianCalendar(),
                organizers = listOf(),
                selectOrganizers = listOf(),
                selectOrganizer = Organizer.default,
                expanded = false,
                event = EventInfo.emptyEvent,
                isLoadUpdate = false,
                isErrorUpdate = false,
                inputText = "",
                isLoadDelete = false,
                isErrorDelete = false,
                showSelectDate = false,
                enableUpdateButton = true
            )
        }
    }

    sealed interface Label {
        object Close : Label
    }
}