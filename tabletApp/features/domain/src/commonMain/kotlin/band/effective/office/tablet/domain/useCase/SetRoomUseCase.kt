package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.Settings

class SetRoomUseCase {
    operator fun invoke(nameRoom: String) =
        Settings.current.updateSettings(nameRoom)
}