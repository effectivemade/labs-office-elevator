package band.effective.office.tv.di.view_models

import band.effective.office.tv.network.use_cases.AuthSynologyUseCase
import band.effective.office.tv.network.use_cases.PhotoSynologyUseCase
import band.effective.office.tv.repository.AuthSynologyRepository
import band.effective.office.tv.repository.PhotoSynologyRepository
import band.effective.office.tv.repository.SynologyRepository
import band.effective.office.tv.repository.impl.AuthSynologyRepositoryImpl
import band.effective.office.tv.repository.impl.PhotoSynologyRepositoryImpl
import band.effective.office.tv.repository.impl.SynologyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface PhotoViewModelModule {

    @ViewModelScoped
    @Binds
    fun bindsAuthSynologyRepository(repository: AuthSynologyRepositoryImpl): AuthSynologyRepository

    @ViewModelScoped
    @Binds
    fun bindsPhotoSynologyRepository(repository: PhotoSynologyRepositoryImpl): PhotoSynologyRepository

    @ViewModelScoped
    @Binds
    fun bindsSynologyRepository(repository: SynologyRepositoryImpl): SynologyRepository
}