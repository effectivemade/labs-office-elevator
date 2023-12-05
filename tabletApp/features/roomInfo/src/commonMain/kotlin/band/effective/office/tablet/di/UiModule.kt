package band.effective.office.tablet.di

import band.effective.office.tablet.domain.useCase.CancelUseCase
import band.effective.office.tablet.domain.useCase.CheckBookingUseCase
import band.effective.office.tablet.domain.useCase.CheckSettingsUseCase
import band.effective.office.tablet.domain.useCase.SetRoomUseCase
import band.effective.office.tablet.domain.useCase.UpdateUseCase
import org.koin.dsl.module

val uiModule = module {
    single<UpdateUseCase> { UpdateUseCase(
        roomInfoUseCase = get(),
        organizersInfoUseCase = get(),
        checkSettingsUseCase = get(),
        currentEventController = get()
    ) }
    single<CheckSettingsUseCase> { CheckSettingsUseCase() }
    single<CheckBookingUseCase> { CheckBookingUseCase(
        roomInfoUseCase = get(),
        checkSettingsUseCase = get()
    ) }
    single<CancelUseCase> { CancelUseCase(cancelRepository = get()) }
    single<SetRoomUseCase> { SetRoomUseCase() }
}