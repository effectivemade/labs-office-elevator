package band.effective.office.tablet.ui.updateEvent

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.bookingComponents.pickerDateTime.DateTimePickerComponent
import band.effective.office.tablet.ui.updateEvent.store.UpdateEventStore
import band.effective.office.tablet.ui.updateEvent.store.UpdateEventStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

class UpdateEventComponent(
    private val componentContext: ComponentContext,
    storeFactory: StoreFactory,
    event: EventInfo,
    val room: String,
    onCloseRequest: () -> Unit
) : ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        UpdateEventStoreFactory(
            storeFactory = storeFactory,
            onCloseRequest = onCloseRequest
        ).create(defaultValue = event.toState())
    }

    val dateTimePickerComponent: DateTimePickerComponent =
        DateTimePickerComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onOpenDateTimePickerModal = { store.accept(UpdateEventStore.Intent.OnOpenSelectDateDialog) },
            onCloseRequest = { store.accept(UpdateEventStore.Intent.OnCloseSelectDateDialog) },
            setNewDate = { day: Int, month: Int, year: Int, hour: Int, minute: Int ->
                store.accept(
                    UpdateEventStore.Intent.OnSetDate(
                        year = year,
                        month = month,
                        day = day,
                        hour = hour,
                        minute = minute
                    )
                )
            },
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = store.stateFlow
    val labels = store.labels

    fun sendIntent(intent: UpdateEventStore.Intent) {
        store.accept(intent)
    }

    private fun EventInfo.toState(): UpdateEventStore.State =
        UpdateEventStore.State.defaultValue.copy(
            date = startTime,
            duration = ((finishTime.time.time - startTime.time.time) / 60000).toInt(),
            selectOrganizer = organizer,
            inputText = organizer.fullName,
            event = this
        )
}
