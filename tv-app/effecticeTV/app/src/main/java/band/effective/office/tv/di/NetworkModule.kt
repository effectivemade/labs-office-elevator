package band.effective.office.tv.di

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.EitherDuolingoAdapterFactory
import band.effective.office.tv.core.network.EitherLeaderIdAdapterFactory
import band.effective.office.tv.core.network.EitherSynologyAdapterFactory
import band.effective.office.tv.network.*
import band.effective.office.tv.network.duolingo.DuolingoApi
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.network.mattermost.MattermostApi
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.impl.MattermostWebSocketClientImpl
import band.effective.office.tv.network.synology.SynologyApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import notion.api.v1.NotionClient
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
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi).asLenient()

    @Singleton
    @Provides
    @SynologyRetrofitClient
    fun provideEitherSynologyAdapterFactory(): CallAdapter.Factory =
        EitherSynologyAdapterFactory()

    @Singleton
    @Provides
    @DualingoRetrofitClient
    fun provideEitherDuolingoAdapterFactory(): CallAdapter.Factory =
        EitherDuolingoAdapterFactory()

    @Singleton
    @Provides
    @SynologyRetrofitClient
    fun provideSynologyRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient,
        @SynologyRetrofitClient callAdapter: CallAdapter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(callAdapter)
            .client(client)
            .baseUrl(BuildConfig.apiSynologyUrl)
            .build()

    @Singleton
    @Provides
    @DualingoRetrofitClient
    fun provideDuolingoRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient,
        @DualingoRetrofitClient callAdapter: CallAdapter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(callAdapter)
            .client(client)
            .baseUrl(BuildConfig.duolingoUrl)
            .build()

    @Singleton
    @Provides
    @MattermostClient
    fun provideMattermostRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        @MattermostClient client: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
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
    fun provideApiDuolingo(@DualingoRetrofitClient retrofit: Retrofit) =
        retrofit.create(DuolingoApi::class.java)

    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Singleton
    @Provides
    fun provideApiMattermost(@MattermostClient retrofit: Retrofit): MattermostApi =
        retrofit.create()

    @Singleton
    @Provides
    fun provideMattermostClient(
        okHttpClient: OkHttpClient,
        mattermostApi: MattermostApi
    ): MattermostWebSocketClient =
        MattermostWebSocketClientImpl(okHttpClient, mattermostApi)
    @Singleton
    @Provides
    fun provideNotionClient(): NotionClient {
        return NotionClient(BuildConfig.notionToken)
    }
}