package band.effective.office.elevator.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.ref.WeakReference
import java.util.*

object LastOpenActivityProvider {

    private val nullActivityReference: java.lang.ref.Reference<Activity> = WeakReference(null)

    private val lastOpenActivityFlow = MutableStateFlow(nullActivityReference)

    fun start(context: Context) {
        (context.applicationContext as Application)
            .registerActivityLifecycleCallbacks(object : AbstractActivityLifecycleCallbacks() {
                private val resumedActivities = Stack<WeakReference<Activity>>()

                override fun onActivityResumed(activity: Activity) {
                    resumedActivities.push(WeakReference(activity))
                    lastOpenActivityFlow.value = resumedActivities.peek()
                }

                override fun onActivityPaused(activity: Activity) {
                    if (resumedActivities.isNotEmpty()) {
                        resumedActivities.pop()
                    }
                    if (resumedActivities.isEmpty()) {
                        lastOpenActivityFlow.value = nullActivityReference
                    } else {
                        lastOpenActivityFlow.value = resumedActivities.peek()
                    }
                }
            })
    }

    fun lastOpenedActivity(): Activity? = lastOpenActivityFlow.value.get()
}
