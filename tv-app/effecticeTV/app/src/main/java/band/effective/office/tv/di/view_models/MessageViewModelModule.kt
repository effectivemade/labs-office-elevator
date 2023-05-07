package band.effective.office.tv.di.view_models

import band.effective.office.tv.repository.uselessFactRepository.UselessFactRepository
import band.effective.office.tv.repository.uselessFactRepository.impl.UselessFactRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface MessageViewModelModule {
    @ViewModelScoped
    @Binds
    fun bindsUselessFactRepository(repository: UselessFactRepositoryImpl): UselessFactRepository
}