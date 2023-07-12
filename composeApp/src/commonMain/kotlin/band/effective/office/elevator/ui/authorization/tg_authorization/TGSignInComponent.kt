package band.effective.office.elevator.ui.authorization.tg_authorization

import kotlinx.coroutines.flow.StateFlow

interface TGSignInComponent {
    val nick : StateFlow<String>

    val inProgress : StateFlow<Boolean>

    fun onNickChanged(nick: String)

    fun onTGSignInClick()
}