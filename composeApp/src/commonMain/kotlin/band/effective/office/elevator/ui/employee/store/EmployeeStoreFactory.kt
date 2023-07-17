package band.effective.office.elevator.ui.employee.store

import band.effective.office.elevator.ui.employee.EmployeesData
import band.effective.office.elevator.ui.employee.employeeCard
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

internal class EmployeeStoreFactory (private val storeFactory: StoreFactory):KoinComponent{

    fun create(): EmployeeStore=
        object: EmployeeStore, Store<EmployeeStore.Intent,EmployeeStore.State,EmployeeStore.Label> by storeFactory.create(
            name="EmployeeStore",
            initialState = EmployeeStore.State(
                changeShowedEmployeeCards = EmployeesData.employeesCardData),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerIMPL

        ){

        }
    private sealed interface Msg{
        data class UpdateEmployees(val userMessageState:List<employeeCard>): Msg
    }
    private inner class ExecutorImpl :
        CoroutineExecutor<EmployeeStore.Intent, Nothing, EmployeeStore.State, Msg, EmployeeStore.Label>() {
            override fun executeIntent(intent: EmployeeStore.Intent, getState: () -> EmployeeStore.State) {
                when (intent) {
                    EmployeeStore.Intent.OnUserSearchClick -> {
                        scope.launch {
                            dispatch(Msg.UpdateEmployees(userMessageState = getState().changeShowedEmployeeCards))
                        }
                    }
                    EmployeeStore.Intent.OnClickOnEmployee ->{
                        scope.launch {
                            publish(EmployeeStore.Label.ShowProfileScreen)
                        }
                    }

                }
            }
        }


        private object ReducerIMPL:Reducer<EmployeeStore.State,Msg>{
            override fun EmployeeStore.State.reduce(msg:Msg): EmployeeStore.State =
                when(msg){
                    is Msg.UpdateEmployees -> copy(msg.userMessageState)
                }

        }
}