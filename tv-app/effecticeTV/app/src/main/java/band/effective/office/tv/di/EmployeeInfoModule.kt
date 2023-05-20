package band.effective.office.tv.di

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.repository.notion.EmployeeInfoRepository
import band.effective.office.tv.repository.notion.impl.EmployeeInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import notion.api.v1.NotionClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface EmployeeInfoModule {
    @Singleton
    @Binds
    fun bindEmployeeInfoRepository(impl: EmployeeInfoRepositoryImpl): EmployeeInfoRepository
}