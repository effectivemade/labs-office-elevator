package band.effective.office.tablet.ui.mainScreen.roomInfoComponents

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.store.RoomInfoFactory
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.store.RoomInfoStore
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

class RoomInfoComponent(
    private val componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val onFreeRoomIntent: () -> Unit,
    private val room: () -> RoomInfo
) : ComponentContext by componentContext {
    private val roomInfoStore = instanceKeeper.getStore {
        RoomInfoFactory(storeFactory, room).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = roomInfoStore.stateFlow

    fun sendIntent(intent: RoomInfoStore.Intent) {
        when (intent) {
            is RoomInfoStore.Intent.OnFreeRoomRequest -> onFreeRoomIntent()
            else -> roomInfoStore.accept(intent)
        }
    }
}