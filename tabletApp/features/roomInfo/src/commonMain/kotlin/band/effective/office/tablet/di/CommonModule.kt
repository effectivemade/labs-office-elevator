package band.effective.office.tablet.di

import band.effective.office.tablet.domain.MockController
import band.effective.office.tablet.domain.RoomInteractor
import band.effective.office.tablet.domain.RoomInteractorImpl
import band.effective.office.tablet.network.MockRoomInfoRepository
import band.effective.office.tablet.network.RoomInfoRepository
import org.koin.dsl.module

val commonModule = module {
    single<MockController> { MockController() }
    single<RoomInfoRepository> { MockRoomInfoRepository() }
    single<RoomInteractor> { RoomInteractorImpl(get()) }
}