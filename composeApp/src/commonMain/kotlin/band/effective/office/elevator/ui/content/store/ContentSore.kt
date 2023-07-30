package band.effective.office.elevator.ui.content.store

import com.arkivanov.mvikotlin.core.store.Store

interface ContentStore : Store<ContentStore.Intent, ContentStore.State, ContentStore.Label> {

    sealed interface Intent

    sealed interface Label

    object State

}