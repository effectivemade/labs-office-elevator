package band.effective.office.elevator.ui.authorization.tg_authorization

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TGSignInImpl(componentContext: ComponentContext,
//                   private val authorizationRepository: AuthorizationRepository
                   ) : ComponentContext by componentContext,
    TGSignInComponent {
    override val nick = MutableStateFlow("")
    override val inProgress = MutableStateFlow(false)
    private val coroutineScope = componentCoroutineScope()

    override fun onNickChanged(nick: String) {
        this.nick.value = nick
    }

    override fun onTGSignInClick() {
        coroutineScope.launch {
            inProgress.value = true
//            authorizationRepository.signIn(nick.value)
            inProgress.value = false
        }
    }

    fun ComponentContext.componentCoroutineScope(): CoroutineScope {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

        if (lifecycle.state != Lifecycle.State.DESTROYED) {
            lifecycle.doOnDestroy {
                scope.cancel()
            }
        } else {
            scope.cancel()
        }

        return scope
    }
}