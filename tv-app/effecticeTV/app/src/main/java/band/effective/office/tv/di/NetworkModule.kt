package band.effective.office.tv.di

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.EitherSynologyAdapterFactory
import band.effective.office.tv.network.LeaderIdRetrofitClient
import band.effective.office.tv.network.SynologyRetrofitClient
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.core.network.EitherLeaderIdAdapterFactory
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.network.mattermostWebSocketClient.MattermostWebSocketClient
import band.effective.office.tv.network.mattermostWebSocketClient.impl.TestClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.addAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    @Singleton
    @Provides
    fun provideEitherAdapterFactory(): EitherLeaderIdAdapterFactory = EitherLeaderIdAdapterFactory()

    @Singleton
    @Provides
    @LeaderIdRetrofitClient
    fun provideLeaderIdRetrofit(moshiConverterFactory: MoshiConverterFactory, client: OkHttpClient, eitherLeaderIdAdapterFactory: EitherLeaderIdAdapterFactory): Retrofit =
        Retrofit.Builder().addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(eitherLeaderIdAdapterFactory).client(client)
            .baseUrl(BuildConfig.apiLeaderUrl).build()

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi).asLenient()

    @Singleton
    @Provides
    fun provideEitherSynologyAdapterFactory(): CallAdapter.Factory =
        EitherSynologyAdapterFactory()

    @Singleton
    @Provides
    @SynologyRetrofitClient
    fun provideSynologyRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient,
        callAdapter: CallAdapter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(callAdapter)
            .client(client)
            .baseUrl(BuildConfig.apiSynologyUrl)
            .build()

    @Singleton
    @Provides
    fun provideLeaderApi(@LeaderIdRetrofitClient retrofit: Retrofit): LeaderApi =
        retrofit.create()

    @Singleton
    @Provides
    fun provideApiSynology(@SynologyRetrofitClient retrofit: Retrofit) =
        retrofit.create(SynologyApi::class.java)

    @Singleton
    @Provides
    fun provideMattermostClient(): MattermostWebSocketClient = TestClient()
}