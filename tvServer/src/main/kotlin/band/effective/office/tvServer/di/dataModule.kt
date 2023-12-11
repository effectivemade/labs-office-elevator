package band.effective.office.tvServer.di

import band.effective.office.tvServer.api.leader.LeaderApi
import band.effective.office.tvServer.api.leader.LeaderApiImpl
import band.effective.office.tvServer.api.mattermost.MattermostApi
import band.effective.office.tvServer.api.mattermost.MattermostApiImpl
import band.effective.office.tvServer.utils.defaultHttpClient
import org.koin.dsl.module

val dataModule = module {
    single<LeaderApi> { LeaderApiImpl(defaultHttpClient()) }
    single<MattermostApi> { MattermostApiImpl(defaultHttpClient("9yjj3yeuytb7bbysjtgynmxz5o")) }
}