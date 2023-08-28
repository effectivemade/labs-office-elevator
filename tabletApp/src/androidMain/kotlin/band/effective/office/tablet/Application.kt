package band.effective.office.tablet

import android.app.Application
import band.effective.office.tablet.di.initRoomInfoKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initRoomInfoKoin()
    }
}