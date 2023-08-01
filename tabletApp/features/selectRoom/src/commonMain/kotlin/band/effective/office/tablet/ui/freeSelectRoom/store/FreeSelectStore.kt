package band.effective.office.tablet.ui.freeSelectRoom.store

import com.arkivanov.mvikotlin.core.store.Store

interface FreeSelectStore : Store<FreeSelectStore.Intent, FreeSelectStore.State, Nothing> {
    sealed interface Intent {
        data class OnFreeSelectRequest(val close: (() -> Unit)? = null) : Intent
        data class OnCloseWindowRequest(val close: (() -> Unit)? = null) : Intent
    }

    data class State(
        val isLoad: Boolean,
        val isSuccess: Boolean
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = false,
                    isSuccess = false
                )
        }
    }
}