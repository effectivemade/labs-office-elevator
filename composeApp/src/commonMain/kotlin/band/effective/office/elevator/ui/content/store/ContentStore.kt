package band.effective.office.elevator.ui.content.store

import band.effective.office.elevator.ui.models.ElevatorState
import band.effective.office.elevator.ui.models.ReservedSeat
import com.arkivanov.mvikotlin.core.store.Store
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDate

interface ContentStore : Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> {

    sealed interface Intent

    sealed interface Label

    object State

}