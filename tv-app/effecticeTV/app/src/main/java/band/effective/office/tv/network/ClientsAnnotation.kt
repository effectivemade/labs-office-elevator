package band.effective.office.tv.network

import javax.inject.Qualifier


//this annotations for retrofit
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LeaderIdRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SynologyRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MattermostClient
