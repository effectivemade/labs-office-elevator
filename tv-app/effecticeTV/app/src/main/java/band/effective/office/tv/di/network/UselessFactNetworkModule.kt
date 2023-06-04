package band.effective.office.tv.di.network

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.EitherLeaderIdAdapterFactory
import band.effective.office.tv.network.UselessFactClient
import band.effective.office.tv.network.uselessFact.UselessFactApi
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
class UselessFactNetworkModule {

    @Singleton
    @Provides
    @UselessFactClient
    fun provideUselessFactRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient,
        eitherLeaderIdAdapterFactory: EitherLeaderIdAdapterFactory
    ): Retrofit =
        Retrofit.Builder().addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(eitherLeaderIdAdapterFactory).client(client)
            .baseUrl(BuildConfig.uselessFactsApi).build()

    @Singleton
    @Provides
    fun provideUselessFactApi(@UselessFactClient retrofit: Retrofit): UselessFactApi =
        retrofit.create()


}