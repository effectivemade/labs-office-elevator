package band.effective.office.elevator.ui.employee.allEmployee.store


import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.data.repository.EmployeeRepositoryImpl
import band.effective.office.elevator.domain.useCase.EmployeeUseCase
import band.effective.office.elevator.ui.employee.allEmployee.models.mappers.toUI
import band.effective.office.elevator.utils.changeEmployeeShowedList
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class EmployeeStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    private val employeesInfo: EmployeeUseCase by inject()


    // TODO(Atem Gruzdev) Roman Zuzin fix this, its should be in state
    private val _employList: MutableStateFlow<List<EmployeeInfo>> = MutableStateFlow(listOf())
    val employList = _employList.asStateFlow()
    var employeesNameFilter: String = ""

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): EmployeeStore =
        object : EmployeeStore,
            Store<EmployeeStore.Intent, EmployeeStore.State, EmployeeStore.Label> by storeFactory.create(
                name = "EmployeeStore",
                initialState = EmployeeStore.State(
                    changeShowedEmployeeCards = employList.value.map(EmployeeInfo::toUI),
                    countShowedEmployeeCards = employList.value.count().toString(),
                    countInOfficeShowedEmployeeCards = employList.value.filter { it.state == "In office" }
                        .count().toString(),
                    query = employeesNameFilter

                ),
                bootstrapper = coroutineBootstrapper {
                    launch(Dispatchers.IO) {
                        employeesInfo.invoke().collect { newList ->
                            when (newList) {
                                is Either.Error -> {
                                    // TODO show error on UI}
                                }

                                is Either.Success -> _employList.update { newList.data }
                            }
                        }
                    }
                    launch {
                        dispatch(Action.UpdateEmployeesInfo)
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerIMPL

            ) {

        }

    private sealed interface Msg {
        data class UpdateEmployees(val query: String, val employeesInfo: List<EmployeeInfo>) : Msg
    }

    private sealed interface Action {
        object UpdateEmployeesInfo : Action
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<EmployeeStore.Intent, Action, EmployeeStore.State, Msg, EmployeeStore.Label>() {
        override fun executeIntent(
            intent: EmployeeStore.Intent,
            getState: () -> EmployeeStore.State
        ) {
            when (intent) {
                is EmployeeStore.Intent.OnTextFieldUpdate -> {
                    scope.launch {
                        employeesNameFilter = intent.query
                        dispatch(
                            Msg.UpdateEmployees(
                                query = employeesNameFilter,
                                employeesInfo = employList.value
                            )
                        )
                    }
                }

                is EmployeeStore.Intent.OnClickOnEmployee -> {
                    scope.launch {
                        publish(EmployeeStore.Label.ShowProfileScreen(employList.value.filter { it.id == intent.employeeId }[0]))
                    }
                }

            }
        }

        override fun executeAction(
            action: EmployeeStoreFactory.Action,
            getState: () -> EmployeeStore.State
        ) {
            when (action) {
                is EmployeeStoreFactory.Action.UpdateEmployeesInfo -> {
                    dispatch(
                        Msg.UpdateEmployees(
                            query = employeesNameFilter,
                            employeesInfo = employList.value
                        )
                    )//i need intent.query there so i made special variable
                }
            }
        }
    }

    private object ReducerIMPL : Reducer<EmployeeStore.State, Msg> {
        override fun EmployeeStore.State.reduce(msg: Msg): EmployeeStore.State =
            when (msg) {
                is Msg.UpdateEmployees ->
                    copy(
                        changeShowedEmployeeCards = changeEmployeeShowedList(
                            msg.query,
                            msg.employeesInfo.map(EmployeeInfo::toUI)
                        ),
                        countShowedEmployeeCards = changeEmployeeShowedList(
                            msg.query,
                            msg.employeesInfo.map(EmployeeInfo::toUI)
                        ).count().toString(),
                        countInOfficeShowedEmployeeCards = changeEmployeeShowedList(
                            msg.query,
                            msg.employeesInfo.map(EmployeeInfo::toUI)
                        )
                            .filter { it.state == "In office" }.count().toString(),
                        query = msg.query

                    )
            }

    }

}
