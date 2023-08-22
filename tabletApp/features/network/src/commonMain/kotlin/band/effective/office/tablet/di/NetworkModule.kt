package band.effective.office.tablet.di

import band.effective.office.network.api.Api
import band.effective.office.network.api.impl.ApiImpl
import band.effective.office.network.api.impl.ApiMock
import band.effective.office.utils.MockFactory
import org.koin.dsl.module

val networkModule = module {
    val api = ApiImpl()
    single<Api> { ApiMock(realApi = api, mockFactory = MockFactory()) }
    single { api.collector } // NOTE(Maksim Mishenko) collector
}