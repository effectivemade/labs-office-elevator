package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.network.repository.CancelRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**Class for control start/finish event in room*/
abstract class CurrentEventController(
    private val roomUseCase: RoomInfoUseCase,
    private val cancelRepository: CancelRepository
) {
    private lateinit var job: Job
    protected lateinit var scope: CoroutineScope
    protected var currentEvent: EventInfo? = null
    protected val handlersList: MutableList<() -> Unit> = mutableListOf()

    /**Prepare controller for async work*/
    fun start(scope: CoroutineScope) {
        this.scope = scope
        job = update()
        roomUseCase.subscribe(scope) { onServerUpdate() }
    }

    /**Finish current event*/
    fun cancelCurrentEvent() {
        scope.launch {
            if (cancelRepository.cancelEvent()) {
                onServerUpdate()
            }
        }
    }

    /**Reloading current event state change handler*/
    private fun onServerUpdate() {
        scope.launch {
            job.cancel()
            currentEvent = roomUseCase().currentEvent
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