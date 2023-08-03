package band.effective.office.elevator.ui.booking

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory

class BookingComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
) :
    ComponentContext by componentContext {

//    private val profileStore = instanceKeeper.getStore {
//        ProfileStoreFactory(
//            storeFactory = storeFactory
//        ).create()
//    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    val user: StateFlow<ProfileStore.User> = profileStore.stateFlow
//
//    val label: Flow<ProfileStore.Label> = profileStore.labels
//
//    fun onEvent(event: ProfileStore.Intent) {
//        profileStore.accept(event)
//    }
//
////    fun onOutput(output: Output) {
////        output(output)
////    }
//
//    sealed interface Output {
//        object OpenAuthorizationFlow : Output
//    }

}