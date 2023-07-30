package band.effective.office.elevator.utils

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import band.effective.office.elevator.WifiLocationJobService


object WifiLocationJobServiceSettings {
    fun scheduleJob(context: Context) {
        val serviceComponent = ComponentName(context, WifiLocationJobService::class.java)

        val builder = JobInfo.Builder(0, serviceComponent)

        builder.setMinimumLatency((minTimeLatencySeconds * 1000))
        builder.setOverrideDeadline((maxTimeLatencySeconds * 1000))

        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(builder.build())
    }

    //  TODO: replace this variable to local property or ???
    private const val minTimeLatencySeconds = 30L
    private const val maxTimeLatencySeconds = 60L
}