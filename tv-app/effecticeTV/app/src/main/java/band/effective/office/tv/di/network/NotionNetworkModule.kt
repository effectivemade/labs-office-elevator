package band.effective.office.tv.di.network

import band.effective.office.tv.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import notion.api.v1.NotionClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotionNetworkModule {

    @Singleton
    @Provides
    fun provideNotionClient(): NotionClient {
        return NotionClient(BuildConfig.notionToken)
    }
}