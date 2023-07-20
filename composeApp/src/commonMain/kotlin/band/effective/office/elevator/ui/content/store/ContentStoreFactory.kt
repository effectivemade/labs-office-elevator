package band.effective.office.elevator.ui.content.store

import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ContentStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    val locationTracker: LocationTracker by inject()

    fun create(): ContentStore =
        object : ContentStore,
            Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> by storeFactory.create(
                name = "ContentStore",
                initialState = ContentStore.State,
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        println("HI")
                        locationTracker.getLocationsFlow()
                            .distinctUntilChanged()
                            .collect { println("new location: $it") }
                    }
                    launch {
                        try {
                            locationTracker.startTracking()
                        } catch (e: Throwable) {
                            println("NO permission")
                        }
                    }
                }
            ) {}

    private inner class ExecutorImpl :
        CoroutineExecutor<ContentStore.Intent, Nothing, ContentStore.State, Nothing, ContentStore.Label>()

    private object ReducerImpl : Reducer<ContentStore.State, Nothing> {
        override fun ContentStore.State.reduce(msg: Nothing): ContentStore.State {
            return ContentStore.State
        }
    }

}