package band.effective.office.elevator.common.compose.screens.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

internal class LoginViewModel : ScreenModel {

    sealed class Effect {
        data class SignInFailure(val message: String) : Effect()
        object SignInSuccess : Effect()
    }

    sealed class Action {
        object SignIn : Action()
    }

    private val mutableEffectState = MutableSharedFlow<Effect>()
    val effectState = mutableEffectState.asSharedFlow()

    fun sendAction(action: Action) {
        when (action) {
            Action.SignIn -> GoogleAuthorization.signIn(onSignInSuccess = {
                coroutineScope.launch {
                    mutableEffectState.emit(
                        Effect.SignInSuccess
                    )
                }
            },
                onSignInFailure = { exception ->
                    coroutineScope.launch {
                        mutableEffectState.emit(
                            Effect.SignInFailure(
                                exception.message ?: "Something went wrong. Please try again later"
                            )
                        )
                    }
                })
        }
    }
}