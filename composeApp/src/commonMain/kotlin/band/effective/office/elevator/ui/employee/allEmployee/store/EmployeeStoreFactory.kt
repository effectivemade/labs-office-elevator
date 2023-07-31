package band.effective.office.elevator.ui.employee.allEmployee.store


import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.usecase.EmployeeUseCase
import band.effective.office.elevator.ui.employee.allEmployee.EmployeesData
import band.effective.office.elevator.utils.changeEmployeeShowedList
import band.effective.office.elevator.utils.convertEmployeeInfoListToEmployeeCard
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class EmployeeStoreFactory(private val storeFactory: StoreFactory):KoinComponent{

    private val employeesInfo:EmployeeUseCase by inject()

    var employeesInfoList:List<EmployeeInfo> = EmployeesData.employeesCardData//listOf()
    var employeesNameFilter:String=""
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): EmployeeStore =
        object: EmployeeStore, Store<EmployeeStore.Intent, EmployeeStore.State, EmployeeStore.Label> by storeFactory.create(
            name="EmployeeStore",
            initialState = EmployeeStore.State(
                changeShowedEmployeeCards = convertEmployeeInfoListToEmployeeCard(employeesInfoList)
            ),
            bootstrapper = coroutineBootstrapper {
                launch(Dispatchers.IO) {
                    employeesInfo.invoke().collect{value->employeesInfoList}
                }
                launch { dispatch(EmployeeStoreFactory.Action.UpdateEmployeesInfo(employeesInfoList))
                }},
            executorFactory = ::ExecutorImpl,
            reducer = ReducerIMPL

        ){

        }
    private sealed interface Msg{
        data class UpdateEmployees(val query: String, val employeesInfo:List<EmployeeInfo>): Msg
    }
    private sealed interface Action{
        data class UpdateEmployeesInfo(val employeesInfo: List<EmployeeInfo>): Action
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<EmployeeStore.Intent, Action, EmployeeStore.State, Msg, EmployeeStore.Label>() {
        override fun executeIntent(intent: EmployeeStore.Intent, getState: () -> EmployeeStore.State) {
            when (intent) {
                is EmployeeStore.Intent.OnTextFieldUpdate -> {
                    scope.launch {
                        employeesNameFilter=intent.query
                        dispatch(Msg.UpdateEmployees(query = employeesNameFilter, employeesInfo = employeesInfoList))//employeesInfoList
                    }
                }
                EmployeeStore.Intent.OnClickOnEmployee ->{
                    scope.launch {
                        publish(EmployeeStore.Label.ShowProfileScreen)
                    }
                }

            }
        }
        override fun executeAction(
            action: EmployeeStoreFactory.Action,
            getState: () -> EmployeeStore.State
        ) {
            when(action){
                is EmployeeStoreFactory.Action.UpdateEmployeesInfo->{
                    employeesInfoList=action.employeesInfo//???
                    dispatch(Msg.UpdateEmployees(query = employeesNameFilter, employeesInfo = employeesInfoList))//i need intent.query there so i made special variable
                }
            }
        }
    }

    private object ReducerIMPL: Reducer<EmployeeStore.State, Msg> {
        override fun EmployeeStore.State.reduce(msg: Msg): EmployeeStore.State =
            when(msg){
                is Msg.UpdateEmployees -> copy(
                    changeShowedEmployeeCards = changeEmployeeShowedList(msg.query,convertEmployeeInfoListToEmployeeCard(msg.employeesInfo))

                )
            }

    }

}
