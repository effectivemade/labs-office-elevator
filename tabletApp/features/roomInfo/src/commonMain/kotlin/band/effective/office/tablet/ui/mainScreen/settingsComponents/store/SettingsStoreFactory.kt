package band.effective.office.tablet.ui.mainScreen.settingsComponents.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import org.koin.core.component.KoinComponent

class SettingsStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): SettingsStore =
        object : SettingsStore,
            Store<SettingsStore.Intent, SettingsStore.State, Nothing> by storeFactory.create(
                name = "SettingsStore",
                initialState = SettingsStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {},
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
    }


    private sealed interface Message {

    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<SettingsStore.Intent, Action, SettingsStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: SettingsStore.Intent,
            getState: () -> SettingsStore.State
        ) {
            when (intent) {
                is SettingsStore.Intent.OnExitApp -> {}
                is SettingsStore.Intent.SaveData -> {}
            }
        }

        override fun executeAction(action: Action, getState: () -> SettingsStore.State) {
            when (action) {
                else -> {}
            }
        }

    }

    private object ReducerImpl : Reducer<SettingsStore.State, Message> {
        override fun SettingsStore.State.reduce(message: Message): SettingsStore.State =
            when (message) {
                else -> copy()
            }
    }
}