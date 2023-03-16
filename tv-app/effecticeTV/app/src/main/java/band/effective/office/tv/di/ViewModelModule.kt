package band.effective.office.tv.di

import band.effective.office.tv.repository.leaderIdRepository.LeaderIdEventsInfoRepository
import band.effective.office.tv.screen.LeaderIdEvets.LeaderIdEventsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {
    @Singleton
    @Provides
    fun provideLeaderIdEventsViewModel(leaderIdEventsInfoRepository: LeaderIdEventsInfoRepository): LeaderIdEventsViewModel =
        LeaderIdEventsViewModel(leaderIdEventsInfoRepository)
}