package band.effective.office.tvServer.di

import band.effective.office.tvServer.api.leader.LeaderApi
import band.effective.office.tvServer.api.leader.LeaderApiImpl
import band.effective.office.tvServer.api.mattermost.MattermostApi
import band.effective.office.tvServer.api.mattermost.MattermostApiImpl
import band.effective.office.tvServer.utils.defaultHttpClient
import band.effective.office.tvServer.utils.firebase
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.dsl.module

val dataModule = module {
    single<LeaderApi> { LeaderApiImpl(defaultHttpClient()) }
    single<MattermostApi> { MattermostApiImpl(defaultHttpClient(System.getenv("APIKEY") ?: "")) }
    single<FirebaseMessaging> { firebase() }
}