package band.effective.office.tablet.ui.bookingComponents.pickerDateTime

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

class DateTimePickerComponent(
    private val componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val onOpenDateTimePickerModal: () -> Unit,
    private val onCloseRequest: () -> Unit,
    private val setNewDate: (Int, Int, Int, Int, Int) -> Unit,
) : ComponentContext by componentContext {

    private val dateTimePickerStore = instanceKeeper.getStore {
        DateTimePickerStoreFactory(storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = dateTimePickerStore.stateFlow

    fun sendIntent(intent: DateTimePickerStore.Intent) {
        when (intent) {
            is DateTimePickerStore.Intent.OnDateTimePickerModal -> onOpenDateTimePickerModal()
            is DateTimePickerStore.Intent.CloseModal -> onCloseRequest()
            is DateTimePickerStore.Intent.OnSetDate -> {
                setNewDate(
                    intent.changedDay,
                    intent.changedMonth,
                    intent.changedYear,
                    intent.changedHour,
                    intent.changedMinute
                )
            }
        }
    }
}