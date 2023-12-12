package band.effective.office.tvServer.di

import band.effective.office.tvServer.repository.leaderId.LeaderRepository
import band.effective.office.tvServer.repository.leaderId.LeaderRepositoryImpl
import band.effective.office.tvServer.repository.mattermost.ChatRepository
import band.effective.office.tvServer.repository.mattermost.MattermostRepository
import band.effective.office.tvServer.repository.message.FirebaseMessageRepository
import band.effective.office.tvServer.repository.message.MessageRepository
import org.koin.dsl.module

val domainModule = module {
    single<LeaderRepository> { LeaderRepositoryImpl(get()) }
    single<MattermostRepository> { ChatRepository(get()) }
    single<MessageRepository> { FirebaseMessageRepository(firebase = get(), topicName = "Mattermost") }
}