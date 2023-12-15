package band.effective.office.tvServer.di

import band.effective.office.tvServer.repository.message.MessageRepository
import band.effective.office.tvServer.service.LeaderIdService
import band.effective.office.tvServer.service.mattermost.MattermostService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {
    single<LeaderIdService> { LeaderIdService(leaderRepository = get()) }
    single<MattermostService> {

        MattermostService(
            mattermostRepository = get(),
            simpleMessageRepository = get<MessageRepository>(qualifier = named("simple")),
            importantMessageRepository = get<MessageRepository>(qualifier = named("important")),
            postRepository = get()
        )
    }
}