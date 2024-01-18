package band.effective.office.tablet.di

import android.content.Context
import org.koin.core.KoinApplication

expect fun initKoin(context: Context): KoinApplication
