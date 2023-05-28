package band.effective.office.tv.di

import band.effective.office.tv.domain.botLogic.MessengerBot
import band.effective.office.tv.domain.botLogic.impl.MattermostBot
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import band.effective.office.tv.repository.duolingo.impl.DuolingoRepositoryImpl
import band.effective.office.tv.repository.leaderId.LeaderIdEventsInfoRepository
import band.effective.office.tv.repository.leaderId.impl.LeaderIdEventsInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Singleton
    @Binds
    fun provideLeaderIdEventsInfoRepository(leaderIdEventsInfoRepositoryImpl: LeaderIdEventsInfoRepositoryImpl): LeaderIdEventsInfoRepository

    @Singleton
    @Binds
    fun provideDuolingoRepository(duolingoRepositoryImpl: DuolingoRepositoryImpl): DuolingoRepository

    @Singleton
    @Binds
    fun provideMessengerBot(mattermostBot: MattermostBot): MessengerBot
}