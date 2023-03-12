package band.effective.office.tv.di

import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.repository.SynologyPhoto
import band.effective.office.tv.repository.impl.SynologyPhotoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PhotoViewModelModule {

    @Singleton
    @Provides
    fun provideSynologyRepository(api: SynologyApi): SynologyPhoto = SynologyPhotoImpl(api)
}