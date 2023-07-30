package band.effective.office.elevator

import android.app.job.JobParameters
import android.app.job.JobService
import band.effective.office.elevator.domain.wifiLocationController.WifiLocationController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WifiLocationJobService(
    private val wifiLocationController: WifiLocationController
): JobService() {
    override fun onStartJob(jobParameters: JobParameters?): Boolean {
        CoroutineScope(Dispatchers.IO).launch {
            wifiLocationController.updateInfoAboutWifiConnection()
        }
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }
}