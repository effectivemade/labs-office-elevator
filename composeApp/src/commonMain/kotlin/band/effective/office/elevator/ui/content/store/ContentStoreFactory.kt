package band.effective.office.elevator.ui.content.store

import com.arkivanov.mvikotlin.core.store.Bootstrapper
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.icerock.moko.geo.LocationTracker
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ContentStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {

    val locationTracker: LocationTracker by inject ()

    fun create(): ContentStore =
        object : ContentStore, Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> by storeFactory.create(
            name = "ContentStore",
            initialState = ContentStore.State,
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private inner class ExecutorImpl :
        CoroutineExecutor<ContentStore.Intent, Nothing, ContentStore.State, Nothing, ContentStore.Label>()

    private object ReducerImpl : Reducer<ContentStore.State, Nothing> {
        override fun ContentStore.State.reduce(msg: Nothing): ContentStore.State {
            return ContentStore.State
        }
    }

    private inner class BootstrapperImpl: Bootstrapper<Unit> {
        override fun dispose() {
            TODO("Not yet implemented")
        }

        override fun invoke() {
            TODO("Not yet implemented")
        }

        override fun init(actionConsumer: (Unit) -> Unit) {
            TODO("Not yet implemented")
        }

    }


}