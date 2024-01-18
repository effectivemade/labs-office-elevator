package band.effective.office.tablet

import android.app.Application
import band.effective.office.tablet.di.initKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}