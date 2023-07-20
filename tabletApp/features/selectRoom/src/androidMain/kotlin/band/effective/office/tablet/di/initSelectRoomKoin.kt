package band.effective.office.tablet.di

import band.effective.office.tablet.di.selectRoomModule
import org.koin.core.context.startKoin

fun initSelectRoomKoin() =  startKoin {
    modules(selectRoomModule)
}