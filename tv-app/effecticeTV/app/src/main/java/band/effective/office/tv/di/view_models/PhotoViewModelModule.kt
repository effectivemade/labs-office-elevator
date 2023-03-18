package band.effective.office.tv.di.view_models

import band.effective.office.tv.repository.PhotoSynologyRepository
import band.effective.office.tv.repository.impl.PhotoSynologyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotoViewModelModule {

    @Singleton
    @Binds
    abstract fun bindsSynologyRepository(repository: PhotoSynologyRepositoryImpl): PhotoSynologyRepository
}