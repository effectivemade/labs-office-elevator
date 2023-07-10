package band.effective.office.tablet.di

import org.koin.core.context.startKoin

fun initRoomInfoKoin() = startKoin {
    modules(commonModule, selectRoomModule)
}