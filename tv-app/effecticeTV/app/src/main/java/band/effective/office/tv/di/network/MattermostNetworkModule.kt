package band.effective.office.tv.di.network

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.EitherMattermostAdapterFactory
import band.effective.office.tv.network.AuthInterceptor
import band.effective.office.tv.network.MattermostClient
import band.effective.office.tv.network.mattermost.MattermostApi
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
class MattermostNetworkModule {

    @Singleton
    @Provides
    @MattermostClient
    fun provideAuthInterceptor() : AuthInterceptor = AuthInterceptor(BuildConfig.mattermostBotToken)

    @Singleton
    @Provides
    @MattermostClient
    fun provideMattermostOkHttpClient(@MattermostClient authInterceptor: AuthInterceptor) = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(BuildConfig.mattermostBotToken))
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
        .build()

    @Singleton
    @Provides
    @MattermostClient
    fun provideEitherMattermostAdapterFactory(): CallAdapter.Factory =
        EitherMattermostAdapterFactory()

    @Singleton
    @Provides
    @MattermostClient
    fun provideMattermostRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        @MattermostClient client: OkHttpClient,
        @MattermostClient callAdapter: CallAdapter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(callAdapter)
            .client(client)
            .baseUrl("https://${BuildConfig.apiMattermostUrl}")
            .build()

    @Singleton
    @Provides
    fun provideApiMattermost(@MattermostClient retrofit: Retrofit): MattermostApi =
        retrofit.create()
}