package band.effective.office.tv.di.network

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.EitherLeaderIdAdapterFactory
import band.effective.office.tv.network.LeaderIdRetrofitClient
import band.effective.office.tv.network.leader.LeaderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LeaderIdNetworkModule {
    @Singleton
    @Provides
    @LeaderIdRetrofitClient
    fun provideEitherAdapterFactory(): EitherLeaderIdAdapterFactory = EitherLeaderIdAdapterFactory()

    @Singleton
    @Provides
    @LeaderIdRetrofitClient
    fun provideLeaderIdRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient,
        eitherLeaderIdAdapterFactory: EitherLeaderIdAdapterFactory
    ): Retrofit =
        Retrofit.Builder().addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(eitherLeaderIdAdapterFactory).client(client)
            .baseUrl(BuildConfig.apiLeaderUrl).build()

    @Singleton
    @Provides
    fun provideLeaderApi(@LeaderIdRetrofitClient retrofit: Retrofit): LeaderApi =
        retrofit.create()

}