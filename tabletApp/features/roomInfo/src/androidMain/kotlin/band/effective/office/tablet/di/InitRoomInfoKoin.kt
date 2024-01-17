package band.effective.office.tablet.di

import band.effective.office.tablet.features.roomInfo.BuildConfig
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initRoomInfoKoin() = startKoin {
    modules(commonModule, selectRoomModule, platformModule)
}

val platformModule = module {
    //TODO
    val postfix = if (BuildConfig.DEBUG) "-test" else ""
    single(named("FireBaseTopics")) { listOf("workspace", "user", "booking")/*.map { it + postfix }*/ }
}