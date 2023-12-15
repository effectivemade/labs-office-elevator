package band.effective.office.tvServer.di

import band.effective.office.tvServer.repository.leaderId.LeaderRepository
import band.effective.office.tvServer.repository.leaderId.LeaderRepositoryImpl
import band.effective.office.tvServer.repository.mattermost.ChatRepository
import band.effective.office.tvServer.repository.mattermost.MattermostRepository
import band.effective.office.tvServer.repository.message.FirebaseMessageRepository
import band.effective.office.tvServer.repository.message.MessageRepository
import band.effective.office.tvServer.repository.post.MattermostPostRepository
import band.effective.office.tvServer.repository.post.PostRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    single<LeaderRepository> { LeaderRepositoryImpl(api = get()) }
    single<MattermostRepository> { ChatRepository(api = get()) }
    single<MessageRepository>(named("simple")) { FirebaseMessageRepository(firebase = get(), topicName = "Mattermost") }
    single<MessageRepository>(named("important")) { FirebaseMessageRepository(firebase = get(), topicName = "ImportantMattermost") }
    single<PostRepository> { MattermostPostRepository(mattermostApi = get(), directId = System.getenv("MATTERMOST_DIRECT_ID")) }
}