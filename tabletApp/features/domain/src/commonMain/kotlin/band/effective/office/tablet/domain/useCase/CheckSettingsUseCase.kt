package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.Settings

class CheckSettingsUseCase {
    operator fun invoke() =
        Settings.current.checkCurrentRoom()
}