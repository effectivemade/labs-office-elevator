package band.effective.office.elevator.ui.bottomSheets.sbp.store

import band.effective.office.elevator.domain.useCase.BankFilterUseCase
import band.effective.office.elevator.domain.useCase.SBPBanksInfoUseCase
import band.effective.office.elevator.expects.pickSBP
import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SBPSoreFactory (
    private val storeFactory: StoreFactory,
    private val userPhoneNumber: String
) : KoinComponent {

    val spbBanksInfo: SBPBanksInfoUseCase by inject()
    val bankFilterUseCase: BankFilterUseCase by inject()

    fun create(): SBPStore =
        object : SBPStore,
            Store<SBPStore.Intent, SBPStore.State, Unit> by storeFactory.create(
                name = "SBPStore",
                initialState = SBPStore.State.initialState,
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
                bootstrapper = SimpleBootstrapper(Action.InitBankInfo)
            ) {}


    private inner class ExecutorImpl :
        CoroutineExecutor<SBPStore.Intent, SBPSoreFactory.Action, SBPStore.State, Msg, Unit>() {

        override fun executeIntent(intent: SBPStore.Intent, getState: () -> SBPStore.State) {
           when(intent) {
               is SBPStore.Intent.OnChangeQuery ->
                   updateQuery(query = intent.query, banks = getState().allBanks)

               is SBPStore.Intent.OnClickBank -> {
                  pickSBP(phoneNumber = userPhoneNumber, bankInfo = intent.bank)
               }
           }
        }

        override fun executeAction(action: Action, getState: () -> SBPStore.State) {
            when (action) {
                Action.InitBankInfo -> loadBankIfo()
            }
        }

        private fun updateQuery(query: String, banks: List<SBPBankInfo>) {
            val updatedBanks = bankFilterUseCase.execute(
                banksList = banks, query = query
            )
            dispatch(Msg.UpdateQuery(query))
            dispatch(Msg.UpdateShowedBanks(updatedBanks))
        }

        private fun loadBankIfo() {
            scope.launch {
                spbBanksInfo.execute().collect { response ->
                    when (response) {
                        is Either.Error -> {
                            //TODO show error
                        }
                        is Either.Success -> dispatch(Msg.UpdateBankList(response.data))
                    }
                }
            }
        }
    }


    private object ReducerImpl : Reducer<SBPStore.State, Msg> {
        override fun SBPStore.State.reduce(msg: Msg): SBPStore.State =
            when(msg) {
                is Msg.UpdateBankList -> copy(showingBanks = msg.newValue, allBanks = msg.newValue)
                is Msg.UpdateQuery -> copy(query = msg.newValue)
                is Msg.UpdateShowedBanks -> copy(showingBanks = msg.newValue)
            }
    }

    private sealed interface Action {
        data object InitBankInfo : Action
    }
    private sealed interface Msg {
        data class UpdateBankList(val newValue: List<SBPBankInfo>) : Msg

        data class UpdateQuery(val newValue: String) : Msg

        data class UpdateShowedBanks(val newValue: List<SBPBankInfo>) : Msg
    }
}
