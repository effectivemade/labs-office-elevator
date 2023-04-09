package band.effective.office.tv.screen.eventStory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.domain.models.Employee.*
import band.effective.office.tv.network.use_cases.EmployeeInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EventStoryViewModel @Inject constructor(private var employeeInfoUseCase: EmployeeInfoUseCase) :
    ViewModel() {
    private val _uiState =
        MutableStateFlow(LatestEventInfoUiState.empty)
    val uiState = _uiState.asStateFlow()
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO


    init {
        fetchBirthdays()
    }

    private fun fetchBirthdays() {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                employeeInfoUseCase.getLatestBirthdays().catch { exception ->
                    _uiState.update {
                        it.copy(
                            isError = true,
                            errorText = exception.message ?: "",
                            isLoading = false,
                            isLoaded = true
                        )
                    }
                }.collect { events ->
                    val resultList = processEmployeeInfo(events)
                    _uiState.update {
                        it.copy(
                            eventsInfo = resultList,
                            isLoading = false,
                            isLoaded = true
                        )
                    }
                }

            }
        }
    }

}

