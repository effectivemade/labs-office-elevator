package band.effective.office.tv.di.view_models

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.repository.SynologyPhoto
import band.effective.office.tv.repository.impl.SynologyPhotoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotoViewModelModule {

    @Singleton
    @Binds
    abstract fun bindsSynologyRepository(repository: SynologyPhotoImpl): SynologyPhoto
}