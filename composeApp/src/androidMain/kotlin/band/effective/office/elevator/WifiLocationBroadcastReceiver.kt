package band.effective.office.elevator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import band.effective.office.elevator.utils.WifiLocationJobServiceSettings

class WifiLocationBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(conext: Context, p1: Intent?) {
        WifiLocationJobServiceSettings.scheduleJob(conext)
    }
}