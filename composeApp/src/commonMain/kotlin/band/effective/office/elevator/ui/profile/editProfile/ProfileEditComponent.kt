package band.effective.office.elevator.ui.profile.editProfile

import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class ProfileEditComponent (
    componentContext: ComponentContext,
    storeFactory: StoreFactory) : ComponentContext by componentContext {

            private val profileEditStore = instanceKeeper.getStore {
                    ProfileEditStoreFactory(
                            storeFactory = storeFactory
                    ).create()
            }

        @OptIn(ExperimentalMviKotlinApi::class)
        val user: StateFlow<ProfileEditStore.User> = profileEditStore.stateFlow

        val label: Flow<ProfileEditStore.Label> = profileEditStore.labels

        fun onEvent(event:ProfileEditStore.Intent){
                profileEditStore.accept(event)
        }

        sealed interface Output {
                object OpenProfileFlow:Output
        }
}