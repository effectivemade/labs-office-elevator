package band.effective.office.elevator.ui.authorization.store

import androidx.compose.runtime.Composable
import com.arkivanov.mvikotlin.core.store.Store
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc

interface AuthorizationStore :
    Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> {

    sealed interface Intent {
        object SignInButtonClicked : Intent
    }

    class State

    sealed interface Label {
        object AuthorizationSuccess : Label

        data class AuthorizationFailure(val message: String) : Label
    }
}