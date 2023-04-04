package band.effective.office.tv.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.domain.LatestEmployeeUiState
import band.effective.office.tv.domain.models.Employee.Anniversary
import band.effective.office.tv.domain.models.Employee.Birthday
import band.effective.office.tv.domain.models.Employee.EmployeeInfo
import band.effective.office.tv.domain.models.Employee.NewEmployee
import band.effective.office.tv.useCases.BirthdaysUseCase
import band.effective.office.tv.utils.DateUtlils.getYearsWithTheCompany
import band.effective.office.tv.utils.DateUtlils.isCelebrationToday
import band.effective.office.tv.utils.DateUtlils.isNewEmployeeToday
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch

class BirthdayScreenViewModel(
    private val birthdaysUseCase: BirthdaysUseCase,
) : ViewModel() {
    val uiState =
        MutableStateFlow<LatestEmployeeUiState>(LatestEmployeeUiState.Success(emptyList()))
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    init {
        fetchBirthdays()
    }

    private fun fetchBirthdays() {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                birthdaysUseCase.getLatestBirthdays().catch { exception ->
                    uiState.value = LatestEmployeeUiState.Error(exception)
                }.collect { birthdays ->
                    val resultList = mutableListOf<EmployeeInfo>()
                    birthdays.map {
                        Log.d("BirthdayScreenViewModel", it.startDate)
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
                    uiState.value = LatestEmployeeUiState.Success(resultList)
                }
            }
        }
    }


}