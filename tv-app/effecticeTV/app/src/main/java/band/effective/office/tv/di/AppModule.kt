package band.effective.office.tv.di

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.EitherSynologyAdapterFactory
import band.effective.office.tv.network.LeaderIdRetrofitClient
import band.effective.office.tv.network.SynologyRetrofitClient
import band.effective.office.tv.network.synology.SynologyApi
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @OptIn(ExperimentalStdlibApi::class)
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addAdapter(Rfc3339DateJsonAdapter().nullSafe())
        .build()

    @Provides
    fun loggerInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(
            moshi
        )

    @Singleton
    @Provides
    @LeaderIdRetrofitClient
    fun provideLeaderIdRetrofit(moshiConverterFactory: MoshiConverterFactory, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .client(client)
            .baseUrl(BuildConfig.apiLeaderUrl)
            .build()

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
    fun provideApiSynology(@SynologyRetrofitClient retrofit: Retrofit) =
        retrofit.create(SynologyApi::class.java)
}