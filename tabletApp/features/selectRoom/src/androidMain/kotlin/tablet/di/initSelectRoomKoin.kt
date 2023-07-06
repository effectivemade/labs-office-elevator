package tablet.di

import org.koin.core.context.startKoin

fun initSelectRoomKoin() =  startKoin {
    modules(selectRoomModule)
}