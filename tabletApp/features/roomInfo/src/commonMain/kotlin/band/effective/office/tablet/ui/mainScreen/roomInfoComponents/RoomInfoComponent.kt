package band.effective.office.tablet.ui.mainScreen.roomInfoComponents

import android.util.Log
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.store.RoomInfoFactory
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.store.RoomInfoStore
import band.effective.office.tablet.utils.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class RoomInfoComponent(
    private val componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val onFreeRoomIntent: () -> Unit

) : ComponentContext by componentContext {
    private val roomInfoStore = instanceKeeper.getStore {
        RoomInfoFactory(storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = roomInfoStore.stateFlow

    fun sendIntent(intent: RoomInfoStore.Intent){
        when (intent){
            is RoomInfoStore.Intent.OnFreeRoomRequest -> onFreeRoomIntent()
            else -> roomInfoStore.accept(intent)
        }
    }

    init {
        componentContext.componentCoroutineScope().launch {
            state.collect{ Log.e("looog", it.toString())}
        }
    }
}