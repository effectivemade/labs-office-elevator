package band.effective.office.tvServer.di

import band.effective.office.tvServer.service.LeaderIdService
import band.effective.office.tvServer.service.mattermost.MattermostService
import org.koin.dsl.module

val presentationModule = module {
    single<LeaderIdService> { LeaderIdService(leaderRepository = get()) }
    single<MattermostService> {
        MattermostService(
            mattermostRepository = get(),
            messageRepository = get(),
            postRepository = get()
        )
    }
}