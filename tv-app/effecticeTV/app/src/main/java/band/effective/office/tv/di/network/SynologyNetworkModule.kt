package band.effective.office.tv.di.network

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.EitherSynologyAdapterFactory
import band.effective.office.tv.network.DuolingoRetrofitClient
import band.effective.office.tv.network.SynologyClient
import band.effective.office.tv.network.synology.SynologyApi
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
class SynologyNetworkModule {

    @Singleton
    @Provides
    @SynologyClient
    fun provideEitherSynologyAdapterFactory(): CallAdapter.Factory =
        EitherSynologyAdapterFactory()

    @Singleton
    @Provides
    @SynologyClient
    fun provideSynologyRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        @band.effective.office.tv.network.UnsafeOkHttpClient client: OkHttpClient,
        @DuolingoRetrofitClient callAdapter: CallAdapter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(callAdapter)
            .client(client)
            .baseUrl(BuildConfig.apiSynologyUrl)
            .build()

    @Singleton
    @Provides
    fun provideApiSynology(@SynologyClient retrofit: Retrofit) =
        retrofit.create(SynologyApi::class.java)

}