package band.effective.office.tv.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.R
import band.effective.office.tv.domain.LatestEmployeeUiState
import band.effective.office.tv.domain.models.Employee.Anniversary
import band.effective.office.tv.domain.models.Employee.Birthday
import band.effective.office.tv.domain.models.Employee.EmployeeInfo
import band.effective.office.tv.domain.models.Employee.NewEmployee
import band.effective.office.tv.useCases.BirthdaysUseCase
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class BirthdayScreenViewModel(
    private val birthdaysUseCase: BirthdaysUseCase,
    private val application: Application
) : AndroidViewModel(application) {
    val uiState =
        MutableStateFlow<LatestEmployeeUiState>(LatestEmployeeUiState.Success(emptyList()))
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    init {
        fetchBirthdays()
    }

    fun fetchBirthdays() {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                birthdaysUseCase.getLatestBirthdays().catch { exception ->
                    uiState.value = LatestEmployeeUiState.Error(exception)
                }.collect { birthdays ->
                    val resultList = mutableListOf<EmployeeInfo>()
                    birthdays.map {
                        Log.d("ViewModelTest", "${it.startDate}")
                        if (isCelebrationToday(it.nextBirthdayDate)) {
                            resultList.add(
                                Birthday(
                                    it.firstName,
                                    it.photoUrl,
                                    getBirthdayCongratulations()
                                )
                            )
                        } else if (isCelebrationToday(it.startDate)) {
                            resultList.add(
                                Anniversary(
                                    it.firstName,
                                    it.photoUrl,
                                    getAnniversaryCongratulations(it.startDate)
                                )
                            )
                        } else if (isNewEmployeeToday(it.startDate)) {
                            resultList.add(
                                NewEmployee(
                                    it.firstName,
                                    it.photoUrl,
                                    getNewEmployeeCongratulations()
                                )
                            )
                        } else {
                        }
                    }
                    uiState.value = LatestEmployeeUiState.Success(resultList)
                }
            }
        }
    }

    private fun isCelebrationToday(date: String): Boolean {
        val dateInfo = date.split('-')
        val dayOfMonth = dateInfo[2]
        val monthNumber = dateInfo[1]
        val calendar = Calendar.getInstance()
        Log.d("ViewModelTest", calendar.get(Calendar.DAY_OF_MONTH).toString())
        return (calendar.get(Calendar.DAY_OF_MONTH).toString() == dayOfMonth && calendar.get(
            Calendar.MONTH + 1
        ).toString() == monthNumber)
    }

    private fun isNewEmployeeToday(date: String): Boolean {
        val dateInfo = date.split('-')
        val dayOfMonth = dateInfo[2].toInt()
        val monthNumber = dateInfo[1].toInt()
        val year = dateInfo[0].toInt()
        val employeeStartWorkingDay = Calendar.getInstance()
        employeeStartWorkingDay.set(year, monthNumber - 1, dayOfMonth)
        val startDate = Calendar.getInstance().add(Calendar.DAY_OF_MONTH, -7)
        val endDate = Calendar.getInstance().add(Calendar.DAY_OF_MONTH, 7)
        val dateToCheck: Calendar = Calendar.getInstance()
        dateToCheck.set(2022, 5, 15)
        return (employeeStartWorkingDay.after(startDate) && employeeStartWorkingDay.before(endDate))
    }

    private fun getBirthdayCongratulations(): String {
        return getApplication<Application>().getString(R.string.congratulations_birthday)
    }

    private fun getAnniversaryCongratulations(date: String): String {
        val dateInfo = date.split('-')
        val result = StringBuilder()
        return with(result) {
            append(getApplication<Application>().getString(R.string.with_us))
            append(
                (Calendar.getInstance()
                    .get(Calendar.YEAR) - dateInfo[0].toInt()).toString()
            )
            append(getApplication<Application>().getString(R.string.for_years))
            this.toString()
        }
    }

    private fun getNewEmployeeCongratulations(): String {
        return getApplication<Application>().getString(R.string.now_in_team)
    }
}