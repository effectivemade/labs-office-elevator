package band.effective.office.tvServer.di

import band.effective.office.tvServer.repository.leaderId.LeaderRepository
import band.effective.office.tvServer.repository.leaderId.LeaderRepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    single<LeaderRepository> { LeaderRepositoryImpl(get()) }
}