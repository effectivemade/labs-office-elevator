package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store.FreeNegotiationsStoreFactory
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store.FreeNegotiationsStore
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.KoinComponent


class FreeNegotiationsComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    val onEventInfo: () -> EventInfo,
    val onMainScreen: () -> Unit
) : ComponentContext by componentContext, FreeNegotiationsComponent, KoinComponent {

    //SelectRoom

    override fun onIntent(intent: FreeNegotiationsStore.Intent){
        when(intent){
            is FreeNegotiationsStore.Intent.SetEvent -> freeNegotiationsStore.accept(intent.copy(eventInfo = onEventInfo()))
            else -> {}
        }
    }

    private val freeNegotiationsStore = instanceKeeper.getStore {
        FreeNegotiationsStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state = freeNegotiationsStore.stateFlow
}