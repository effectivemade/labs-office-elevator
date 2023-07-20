package band.effective.office.tablet

import android.app.ActivityOptions
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import band.effective.office.tablet.di.initRoomInfoKoin
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val KIOSK_PACKAGE = "band.effective.office.tablet"


// ...

        val context = this
        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE)
                as DevicePolicyManager
        val adminName = AdminReceiver.getComponentName(context)
        val a = dpm.isDeviceOwnerApp(KIOSK_PACKAGE)
        val b = dpm.isDeviceOwnerApp(adminName.packageName)
        val APP_PACKAGES = arrayOf(adminName.packageName, KIOSK_PACKAGE)
        dpm.setLockTaskPackages(adminName, APP_PACKAGES)

        // Set an option to turn on lock task mode when starting the activity.
        val options = ActivityOptions.makeBasic()
        options.setLockTaskEnabled(true)

        // Start our kiosk app's main activity with our lock task mode option.
        val packageManager = context.packageManager
        val launchIntent = packageManager.getLaunchIntentForPackage(KIOSK_PACKAGE)
        if (launchIntent != null) {
      //      context.startActivity(launchIntent, options.toBundle())
        }


        initRoomInfoKoin()
        setContent {
            App(defaultComponentContext(), DefaultStoreFactory())
        }
    }
}