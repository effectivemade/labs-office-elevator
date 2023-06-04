package band.effective.office.tv.di.network

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.EitherDuolingoAdapterFactory
import band.effective.office.tv.network.DuolingoRetrofitClient
import band.effective.office.tv.network.duolingo.DuolingoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DuolingoNetworkModule {
    @Singleton
    @Provides
    @DuolingoRetrofitClient
    fun provideEitherDuolingoAdapterFactory(): CallAdapter.Factory =
        EitherDuolingoAdapterFactory()

    @Singleton
    @Provides
    @DuolingoRetrofitClient
    fun provideDuolingoRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient,
        @DuolingoRetrofitClient callAdapter: CallAdapter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(callAdapter)
            .client(client)
            .baseUrl(BuildConfig.duolingoUrl)
            .build()

    @Singleton
    @Provides
    fun provideApiDuolingo(@DuolingoRetrofitClient retrofit: Retrofit) =
        retrofit.create(DuolingoApi::class.java)
}