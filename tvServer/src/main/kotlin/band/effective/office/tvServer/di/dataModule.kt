package band.effective.office.tvServer.di

import band.effective.office.tvServer.api.leader.LeaderApi
import band.effective.office.tvServer.api.leader.LeaderApiImpl
import band.effective.office.tvServer.utils.defaultHttpClient
import org.koin.dsl.module

val dataModule = module {
    single<LeaderApi> { LeaderApiImpl(defaultHttpClient()) }
}