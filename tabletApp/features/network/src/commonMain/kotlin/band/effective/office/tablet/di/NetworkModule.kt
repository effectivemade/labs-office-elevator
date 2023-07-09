import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.api.WorkApi
import org.koin.dsl.module

val networkModule = module {
    val workApi = WorkApi()
    single<Api> { workApi }
    single<WorkApi> { workApi }
}