package band.effective.office.tablet.ui.mainScreen.components.bookingRoomComponents

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.useCase.CheckBookingUseCase
import band.effective.office.tablet.domain.useCase.UpdateUseCase
import band.effective.office.tablet.utils.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import java.util.GregorianCalendar

class RealBookingRoomComponent(
    private val componentContext: ComponentContext,
    private val onSelectOtherRoom: () -> Unit,
    roomName: String
) :
    ComponentContext by componentContext, BookingRoomComponent, KoinComponent {
    private var mutableState = MutableStateFlow(BookingRoomState.default)
    override val state = mutableState.asStateFlow()

    override val dateTimeComponent: RealDateTimeComponent =
        RealDateTimeComponent(
            childContext("dateTime"),
            changeDay = { changeDate(it) }
        )
    override val eventLengthComponent: RealEventLengthComponent =
        RealEventLengthComponent(childContext("length"),
            changeLength = { changeLength(it) })
    override val eventOrganizerComponent: RealEventOrganizerComponent =
        RealEventOrganizerComponent(
            childContext("organizer"),
            onSelectOrganizer = { selectOrganizer(it) })

    private val updateUseCase: UpdateUseCase by inject()
    private val checkBookingUseCase: CheckBookingUseCase by inject()

    init {
        mutableState.update { it.copy(roomName = roomName) }
        updateSelectTime()
        update()
        componentCoroutineScope().launch {
            updateUseCase(scope = componentCoroutineScope(), roomUpdateHandler = {
                componentContext.componentCoroutineScope().launch {
                    mutableState.update {
                        it.copy(
                            organizers = updateUseCase.getOrganizersList(),
                            isBusy = !checkBookingUseCase(state.value.toEvent()),
                            busyEvent = checkBookingUseCase.busyEvent(state.value.toEvent())
                                ?: EventInfo.emptyEvent,
                        )
                    }
                }
            }, organizerUpdateHandler = {})
        }
    }

    override fun sendEvent(event: BookingRoomViewEvent) {
        when (event) {
            is BookingRoomViewEvent.OnBookingCurrentRoom -> {
                onSelectOtherRoom()
            }

            is BookingRoomViewEvent.OnBookingOtherRoom -> {
                onSelectOtherRoom()
            }
        }
    }

    private fun update() = componentCoroutineScope().launch {
        mutableState.update {
            it.copy(
                organizers = updateUseCase.getOrganizersList(),
                isBusy = !checkBookingUseCase(state.value.toEvent()),
                busyEvent = checkBookingUseCase.busyEvent(state.value.toEvent())
                    ?: EventInfo.emptyEvent,
            )
        }
    }

    private fun changeLength(delta: Int) {
        if (state.value.length + delta >= 0) {
            mutableState.update { it.copy(length = it.length + delta) }
            update()
        }
    }

    private fun changeDate(day: Int) {
        val newValue = state.value.selectDate.clone() as Calendar
        newValue.add(Calendar.DAY_OF_MONTH, day)
        mutableState.update { it.copy(selectDate = newValue) }
        update()
    }

    //TODO(Maksim Mishenko): think about while(true)
    private fun updateSelectTime() {
        componentCoroutineScope().launch {
            while (true) {
                val now = GregorianCalendar()
                mutableState.update { it.copy(selectDate = now) }
                delay((60 - now.get(Calendar.SECOND)) * 1000L)
            }
        }
    }

    private fun selectOrganizer(organizer: String) {
        mutableState.update { it.copy(organizer = organizer) }
    }

    private fun BookingRoomState.toEvent(): EventInfo {
        val finishTime = selectDate.clone() as Calendar
        finishTime.add(Calendar.MINUTE, length)
        return EventInfo(
            startTime = selectDate,
            finishTime = finishTime,
            organizer = organizer
        )
    }
}