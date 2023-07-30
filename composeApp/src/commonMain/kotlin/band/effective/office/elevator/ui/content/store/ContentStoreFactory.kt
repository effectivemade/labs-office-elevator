package band.effective.office.elevator.ui.content.store

import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ContentStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    val permissionsController: PermissionsController by inject()

    fun create(): ContentStore =
        object : ContentStore,
            Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> by storeFactory.create(
                name = "ContentStore",
                initialState = ContentStore.State,
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        requestPermissions()
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

    // this function check permission state. If is it not granted, it request permission
    private suspend fun requestPermissions() {
        if (!permissionsController.isPermissionGranted(Permission.LOCATION))
            try {
                permissionsController.providePermission(Permission.LOCATION)
            } catch(deniedAlways: DeniedAlwaysException) {
                // Permission is always denied.
            } catch(denied: DeniedException) {
                // Permission was denied.
            }
    }
}