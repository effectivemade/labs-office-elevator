package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.network.repository.CancelRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class CurrentEventController(
    private val roomUseCase: RoomInfoUseCase,
    private val cancelRepository: CancelRepository
) {
    private lateinit var job: Job
    protected lateinit var scope: CoroutineScope
    protected var currentEvent: EventInfo? = null
    protected val handlersList: MutableList<() -> Unit> = mutableListOf()

    fun start(scope: CoroutineScope) {
        this.scope = scope
        job = update()
        roomUseCase.subscribe(scope) { onServerUpdate() }
    }

    private fun cancelCurrentEvent() {
        scope.launch {
            if (cancelRepository.cancelEvent()) {
                onServerUpdate()
            }
        }
    }

    private fun onServerUpdate() {
        scope.launch {
            job.cancel()
            currentEvent = roomUseCase().currentEvent
            job = update()
        }
    }

    fun subscribe(onEvent: () -> Unit) {
        handlersList.add(onEvent)
    }

    protected abstract fun update(): Job
}