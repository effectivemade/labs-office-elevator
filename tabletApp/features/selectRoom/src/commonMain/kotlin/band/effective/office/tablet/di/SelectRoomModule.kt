package band.effective.office.tablet.di

import org.koin.dsl.module
import band.effective.office.tablet.domain.ISelectRoomInteractor
import band.effective.office.tablet.domain.SelectRoomInteractorImpl

val selectRoomModule = module{
    single<ISelectRoomInteractor> { SelectRoomInteractorImpl(get()) }
}
