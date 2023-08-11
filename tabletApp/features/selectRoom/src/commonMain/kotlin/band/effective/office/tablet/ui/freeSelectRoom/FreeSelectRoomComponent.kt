package band.effective.office.tablet.ui.freeSelectRoom

import band.effective.office.tablet.ui.freeSelectRoom.store.FreeSelectStore
import band.effective.office.tablet.ui.freeSelectRoom.store.FreeSelectStoreFactory
import band.effective.office.tablet.utils.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class FreeSelectRoomComponent(
    private val componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val onCloseRequest: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val store: FreeSelectStore = instanceKeeper.getStore {
        FreeSelectStoreFactory(storeFactory = storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = store.stateFlow
    val labels = store.labels

    fun sendIntent(intent: FreeSelectStore.Intent) {
        store.accept(intent)
    }

    private fun collectLabels() = componentContext.componentCoroutineScope().launch {
        labels.collect {
            when (it) {
                FreeSelectStore.Label.Close -> onCloseRequest()
            }
        }
    }

    init {
        collectLabels()
    }
}