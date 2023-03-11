package band.effective.office.tv.di

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.network.LeaderIdRetrofitClient
import band.effective.office.tv.network.SynologyRetrofitClient
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .build()

    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
                        HttpLoggingInterceptor.Level.BODY
                })
            .build()

    @Singleton
    @Provides
    @LeaderIdRetrofitClient
    fun provideLeaderIdRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    moshi
                ).asLenient()
            )
            .client(client)
            .baseUrl(BuildConfig.apiLeaderUrl) //change base url
            .build()

    @Singleton
    @Provides
    @SynologyRetrofitClient
    fun provideSynologyRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    moshi
                ).asLenient()
            )
            .client(client)
            .baseUrl(BuildConfig.apiLeaderUrl)
            .build()

// do how this
//    @Singleton
//    @Provides
//    fun provideApiLeader(@SynologyRetrofitClient retrofit: Retrofit) =
//        retrofit.create()
}