package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.pickerDateTime

import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStoreFactory
import band.effective.office.tablet.utils.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.Calendar

class DateTimePickerComponent(
    private val componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val onOpenDateTimePickerModal: () -> Unit,
    private val onCloseRequest: () -> Unit,
    private val changeDate: (Int, Int) -> Unit
) : ComponentContext by componentContext {

    private val dateTimePickerStore = instanceKeeper.getStore {
        DateTimePickerStoreFactory(storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = dateTimePickerStore.stateFlow

//    init {
//        componentContext.componentCoroutineScope().launch {
//            state.collect { onChangeDate(state.value.selectDate.clone() as Calendar) }
//        }
//    }

    fun sendIntent(intent: DateTimePickerStore.Intent) {
        when (intent) {
            is DateTimePickerStore.Intent.OnDateTimePickerModal -> onOpenDateTimePickerModal()
            is DateTimePickerStore.Intent.CloseModal -> onCloseRequest()
            is DateTimePickerStore.Intent.OnSetDate -> { day: Int, month: Int -> changeDate(day, month) }
        }
    }
}