package band.effective.office.elevator.android

import android.app.Application
import band.effective.office.elevator.common.compose.Android
import band.effective.office.elevator.common.compose.helpers.LastOpenActivityProvider

class OfficeElevatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Android.applicationContext = this
        LastOpenActivityProvider.start(this)
    }
}