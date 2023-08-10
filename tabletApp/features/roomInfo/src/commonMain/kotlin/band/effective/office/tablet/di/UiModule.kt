package band.effective.office.tablet.di

import band.effective.office.tablet.domain.useCase.CancelUseCase
import band.effective.office.tablet.domain.useCase.CheckBookingUseCase
import band.effective.office.tablet.domain.useCase.CheckSettingsUseCase
import band.effective.office.tablet.domain.useCase.SetRoomUseCase
import band.effective.office.tablet.domain.useCase.UpdateUseCase
import org.koin.dsl.module

val uiModule = module {
    single<UpdateUseCase> { UpdateUseCase(get(), get(), get(), get()) }
    single<CheckBookingUseCase> { CheckBookingUseCase(get(), get()) }
    single<CancelUseCase> { CancelUseCase(get()) }
    single<CheckSettingsUseCase> { CheckSettingsUseCase() }
    single<SetRoomUseCase> { SetRoomUseCase() }
}