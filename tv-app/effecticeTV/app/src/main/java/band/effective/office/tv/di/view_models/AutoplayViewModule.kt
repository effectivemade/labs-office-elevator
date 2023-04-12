package band.effective.office.tv.di.view_models

import band.effective.office.tv.domain.autoplay.AutoplayController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AutoplayViewModule {
    @Singleton
    @Provides
    fun provideAutoplayController() = AutoplayController()
}