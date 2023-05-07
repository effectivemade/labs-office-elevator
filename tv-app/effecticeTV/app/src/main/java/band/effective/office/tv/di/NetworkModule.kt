package band.effective.office.tv.di

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.EitherLeaderIdAdapterFactory
import band.effective.office.tv.core.network.EitherSynologyAdapterFactory
import band.effective.office.tv.network.*
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.network.mattermost.MattermostApi
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.impl.MattermostWebSocketClientImpl
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.network.uselessFact.UselessFactApi
import com.squareup.moshi.Moshi
import dagger.Binds
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
    @MattermostClient
    fun provideMattermostOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(BuildConfig.mattermostBotToken))
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
        .build()

    @Singleton
    @Provides
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
    @UselessFactClient
    fun provideUselessFactRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient,
        eitherLeaderIdAdapterFactory: EitherLeaderIdAdapterFactory
    ): Retrofit =
        Retrofit.Builder().addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(eitherLeaderIdAdapterFactory).client(client)
            .baseUrl("https://uselessfacts.jsph.pl/api/v2/").build()

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
    @MattermostClient
    fun provideMattermostRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        @MattermostClient client: OkHttpClient,
        callAdapter: CallAdapter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(callAdapter)
            .client(client)
            .baseUrl("https://${BuildConfig.apiMattermostUrl}")
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
    fun provideApiMattermost(@MattermostClient retrofit: Retrofit): MattermostApi =
        retrofit.create()

    @Singleton
    @Provides
    fun provideUselessFactApi(@UselessFactClient retrofit: Retrofit): UselessFactApi =
        retrofit.create()
}
@Module
@InstallIn(SingletonComponent::class)
interface NetworkModuleBinds{
    @Singleton
    @Binds
    fun provideMattermostClient(
        mattermostWebSocketClientImpl: MattermostWebSocketClientImpl
    ): MattermostWebSocketClient
}