package band.effective.office.elevator.ui.employee

import band.effective.office.elevator.ui.employee.store.EmployeeStore
import band.effective.office.elevator.ui.employee.store.EmployeeStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class EmployeeComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory//private val
): ComponentContext by componentContext {
    private val employeeStore=
        instanceKeeper.getStore {
            EmployeeStoreFactory(
                storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val employState: StateFlow<EmployeeStore.State> = employeeStore.stateFlow
    val employLabel: Flow<EmployeeStore.Label> = employeeStore.labels

    fun onEvent(event:EmployeeStore.Intent){
        employeeStore.accept(event)
    }

    fun onOutput(output:Output){
        when(output){
            is Output.OpenProfileScreen -> TODO()
            is Output.OpenNewListOfEmployees -> TODO()//not connected
        }
    }


    sealed interface Output{
        object OpenProfileScreen: Output

        object OpenNewListOfEmployees: Output

    }

}