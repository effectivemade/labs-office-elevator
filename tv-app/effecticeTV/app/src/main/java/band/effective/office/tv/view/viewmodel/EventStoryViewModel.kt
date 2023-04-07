package band.effective.office.tv.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.domain.models.Employee.*
import band.effective.office.tv.network.use_cases.EmployeeInfoUseCase
import band.effective.office.tv.view.uiStateModels.LatestEventInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class EventStoryViewModel @Inject constructor(private var employeeInfoUseCase: EmployeeInfoUseCase) :
    ViewModel() {
    private val _uiState =
        MutableStateFlow<LatestEventInfoUiState>(LatestEventInfoUiState.Success(emptyList()))
    val uiState = _uiState.asStateFlow()
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO


    init {
        fetchBirthdays()
    }

    private fun fetchBirthdays() {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                employeeInfoUseCase.getLatestBirthdays().catch { exception ->
                    _uiState.value = LatestEventInfoUiState.Error(exception)
                }.collect { events ->
                    val resultList = processEmployeeInfo(events)
                    _uiState.value = LatestEventInfoUiState.Success(resultList)
                }

            }
        }
    }
}

