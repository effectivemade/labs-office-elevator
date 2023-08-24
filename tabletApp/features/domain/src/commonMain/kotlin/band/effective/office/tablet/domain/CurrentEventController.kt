package band.effective.office.tablet.domain

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Settings
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.network.repository.CancelRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**Class for control start/finish event in room*/
abstract class CurrentEventController(
    private val roomUseCase: RoomInfoUseCase,
    private val cancelRepository: CancelRepository
) {
    private var job: Job? = null
    protected lateinit var scope: CoroutineScope
    protected var currentEvent: EventInfo? = null
    protected val handlersList: MutableList<() -> Unit> = mutableListOf()
    protected var mutableTimeToUpdate = MutableStateFlow(-1L)
    val timeToUpdate = mutableTimeToUpdate.asStateFlow()

    /**Prepare controller for async work*/
    fun start(scope: CoroutineScope) {
        this.scope = scope
        job?.cancel()
        job = update()
        scope.launch {
            roomUseCase.subscribe(Settings.current.checkCurrentRoom(), scope)
                .collect() { onServerUpdate() }
        }
    }

    /**Finish current event*/
    suspend fun cancelCurrentEvent(): Either<ErrorResponse, String> {
        val result = cancelRepository.cancelEvent(currentEvent!!)
        if (result is Either.Success) {
            onServerUpdate()
        }
        return result
    }

    /**Reloading current event state change handler*/
    private fun onServerUpdate() {
        scope.launch {
            job?.cancel()
            currentEvent = when (val response = roomUseCase(Settings.current.checkCurrentRoom())) {
                is Either.Error -> null
                is Either.Success -> response.data.currentEvent
            }
            job = update()
        }
    }

    /**subscribe on current event updates*/
    fun subscribe(onEvent: () -> Unit) {
        handlersList.add(onEvent)
    }

    /**Update current event*/
    protected abstract fun update(): Job

}