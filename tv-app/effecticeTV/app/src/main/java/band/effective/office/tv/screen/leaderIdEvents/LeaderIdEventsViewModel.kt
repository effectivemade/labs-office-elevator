package band.effective.office.tv.screen.leaderIdEvents

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.domain.autoplay.AutoplayableViewModel
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.repository.leaderId.LeaderIdEventsInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LeaderIdEventsViewModel @Inject constructor(
    private val leaderIdEventsInfoRepository: LeaderIdEventsInfoRepository,
    private val timer: TimerSlideShow
) : ViewModel(), AutoplayableViewModel {
    private var mutableState = MutableStateFlow(LeaderIdEventsUiState.empty)
    override val state = mutableState.asStateFlow()
    override fun switchToFirstItem() {
        if (state.value.isPlay) timer.startTimer()
        mutableState.update { it.copy(curentEvent = 0) }
    }

    override fun switchToLastItem() {
        if (state.value.isPlay) timer.startTimer()
        mutableState.update { it.copy(curentEvent = it.eventsInfo.size - 1) }
    }

    val finish = GregorianCalendar()

    init {
        finish.set(Calendar.MONTH, GregorianCalendar().get(Calendar.MONTH) + 1)
        load()
        timer.init(
            scope = viewModelScope,
            callbackToEnd = {
                if (state.value.curentEvent + 1 < state.value.eventsInfo.size) {
                    mutableState.update { it.copy(curentEvent = it.curentEvent + 1) }
                } else {
                    mutableState.update { it.copy(navigateRequest = NavigateRequests.Forward) }
                    timer.stopTimer()
                }
            },
            isPlay = state.value.isPlay
        )
    }

    fun load() = viewModelScope.launch {
        leaderIdEventsInfoRepository.getEventsInfo(finish).collect { either ->
            when {
                either is Either.Failure -> mutableState.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        errorText = it.errorText + "${either.error}\n"
                    )
                }
                either is Either.Success && !state.value.isData -> mutableState.update {
                    it.copy(
                        isLoading = false,
                        isData = true,
                        eventsInfo = it.eventsInfo + either.data,
                        curentEvent = 0,
                        isPlay = true
                    )
                }
                either is Either.Success -> mutableState.update {
                    it.copy(
                        eventsInfo = it.eventsInfo + either.data
                    )
                }
            }
        }
    }

    fun sendEvent(event: LeaderIdScreenEvents) {
        timer.resetTimer()
        when (event) {
            is LeaderIdScreenEvents.OnClickPlayButton -> {
                timer.stopTimer()
                val isPlay = !state.value.isPlay
                mutableState.update { it.copy(isPlay = isPlay) }
                if (isPlay) timer.startTimer()
            }
            is LeaderIdScreenEvents.OnClickNextItem -> {
                if (state.value.curentEvent + 1 < state.value.eventsInfo.size) {
                    mutableState.update { it.copy(curentEvent = it.curentEvent + 1) }
                } else {
                    mutableState.update { it.copy(navigateRequest = NavigateRequests.Forward) }
                    timer.stopTimer()
                }
            }
            is LeaderIdScreenEvents.OnClickPreviousItem -> {
                if (state.value.curentEvent - 1 >= 0) {
                    mutableState.update { it.copy(curentEvent = it.curentEvent - 1) }
                } else {
                    mutableState.update { it.copy(navigateRequest = NavigateRequests.Back) }
                    timer.stopTimer()
                }
            }
        }
    }
}