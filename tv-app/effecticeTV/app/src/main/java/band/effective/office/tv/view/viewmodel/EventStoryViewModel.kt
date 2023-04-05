package band.effective.office.tv.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.domain.LatestEventInfoUiState
import band.effective.office.tv.domain.models.Employee.Anniversary
import band.effective.office.tv.domain.models.Employee.Birthday
import band.effective.office.tv.domain.models.Employee.EmployeeInfo
import band.effective.office.tv.domain.models.Employee.NewEmployee
import band.effective.office.tv.network.use_cases.EmployeeInfoUseCase
import band.effective.office.tv.utils.DateUtlils.getYearsWithTheCompany
import band.effective.office.tv.utils.DateUtlils.isCelebrationToday
import band.effective.office.tv.utils.DateUtlils.isNewEmployeeToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class EventStoryViewModel @Inject constructor(private var employeeInfoUseCase: EmployeeInfoUseCase) :
    ViewModel() {
    val uiState =
        MutableStateFlow<LatestEventInfoUiState>(LatestEventInfoUiState.Success(emptyList()))
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO


    init {
        fetchBirthdays()
    }

    private fun fetchBirthdays() {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                employeeInfoUseCase.getLatestBirthdays().catch { exception ->
                    uiState.value = LatestEventInfoUiState.Error(exception)
                }.collect { birthdays ->
                    val resultList = mutableListOf<EmployeeInfo>()
                    birthdays.map {
                        if (isCelebrationToday(it.nextBirthdayDate)) {
                            resultList.add(
                                Birthday(
                                    it.firstName,
                                    it.photoUrl,
                                )
                            )
                        }
                        if (isCelebrationToday(it.startDate)) {
                            resultList.add(
                                Anniversary(
                                    it.firstName,
                                    it.photoUrl,
                                    getYearsWithTheCompany(it.startDate)
                                )
                            )
                        }
                        if (isNewEmployeeToday(it.startDate)) {
                            resultList.add(
                                NewEmployee(
                                    it.firstName,
                                    it.photoUrl,
                                )
                            )
                        }
                    }
                    uiState.value = LatestEventInfoUiState.Success(resultList)
                }
            }
        }
    }


}