package band.effective.office.tablet.di

import band.effective.office.network.api.Api
import band.effective.office.network.api.impl.ApiImpl
import org.koin.dsl.module

val networkModule = module {
    val api = ApiImpl()
    single<Api> { api/*ApiMock(realApi = api, mockFactory = MockFactory())*/ }
    single/*<Collector>*/ { api.collector }
}