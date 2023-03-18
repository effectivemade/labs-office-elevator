package band.effective.office.elevator.common.compose.screens.login

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class LoginViewModel {

    private val scope = CoroutineScope(Dispatchers.Default)

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
                scope.launch {
                    mutableEffectState.emit(
                        Effect.SignInSuccess
                    )
                }
            },
                onSignInFailure = { exception ->
                    scope.launch {
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