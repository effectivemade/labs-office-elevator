import band.effective.office.network.api.Api
import band.effective.office.network.api.impl.ApiImpl
import band.effective.office.network.api.impl.ApiMock
import band.effective.office.utils.MockFactory
import org.koin.dsl.module

val networkModule = module {
    single<Api> { ApiMock(realApi = ApiImpl(), mockFactory = MockFactory()) }
}