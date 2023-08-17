package band.effective.office.tablet.ui.updateEvent

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
    storeFactory: StoreFactory
) : ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        UpdateEventStoreFactory(storeFactory = storeFactory).create()
    }

    val dateTimePickerComponent: DateTimePickerComponent =
        DateTimePickerComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onOpenDateTimePickerModal = { store.accept(UpdateEventStore.Intent.OnOpenSelectDateDialog) },
            onCloseRequest = { store.accept(UpdateEventStore.Intent.OnCloseSelectDateDialog) },
            setNewDate = { day: Int, month: Int, year: Int, hour: Int, minute: Int ->
                store.accept(UpdateEventStore.Intent.OnSetDate(
                    year = year,
                    month = month,
                    day = day,
                    hour = hour,
                    minute = minute
                ))
            },
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = store.stateFlow
    val labels = store.labels

    fun sendIntent(intent: UpdateEventStore.Intent){
        store.accept(intent)
    }
}