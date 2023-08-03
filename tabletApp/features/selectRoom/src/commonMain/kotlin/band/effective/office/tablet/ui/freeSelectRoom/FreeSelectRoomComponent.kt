package band.effective.office.tablet.ui.freeSelectRoom

import band.effective.office.tablet.ui.freeSelectRoom.store.FreeSelectStore
import band.effective.office.tablet.ui.freeSelectRoom.store.FreeSelectStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.KoinComponent

class FreeSelectRoomComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val onCloseRequest: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val store: FreeSelectStore = instanceKeeper.getStore {
        FreeSelectStoreFactory(storeFactory = storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = store.stateFlow

    fun sendIntent(intent: FreeSelectStore.Intent) {
        when (intent) {
            is FreeSelectStore.Intent.OnCloseWindowRequest -> store.accept(intent.copy(onCloseRequest))
            is FreeSelectStore.Intent.OnFreeSelectRequest -> store.accept(intent.copy(onCloseRequest))
        }
    }
}