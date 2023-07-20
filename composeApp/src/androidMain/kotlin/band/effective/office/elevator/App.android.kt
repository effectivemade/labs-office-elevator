package band.effective.office.elevator

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.BuildCompat.PrereleaseSdkCheck
import band.effective.office.elevator.components.Calendar
import band.effective.office.elevator.di.androidModuleDI
import band.effective.office.elevator.di.appModuleDI
import band.effective.office.elevator.domain.AppActivityLifecycleObserver
import band.effective.office.elevator.ui.ContentView
import band.effective.office.elevator.ui.root.RootComponent
import band.effective.office.elevator.utils.LastOpenActivityProvider
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import dev.icerock.moko.geo.LocationTracker
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import androidx.fragment.app.FragmentManager


class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        LastOpenActivityProvider.start(this)
        Napier.base(DebugAntilog())
        startKoin {
            androidContext(this@AndroidApp)
            modules(appModuleDI, androidModuleDI)
        }
    }
}

class AppActivity : AppCompatActivity() {

    lateinit var appActivityLifecycleObserver: AppActivityLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appActivityLifecycleObserver = AppActivityLifecycleObserver(
            activity = this,
            registry = this.activityResultRegistry
        )
        lifecycle.addObserver(appActivityLifecycleObserver)

        val rootComponent =
            RootComponent(
                componentContext = defaultComponentContext(),
                storeFactory = DefaultStoreFactory(),
            )
        val locationTracker: LocationTracker by inject()
        locationTracker.bind(lifecycle, this, supportFragmentManager )

        setContent {
            ContentView(rootComponent)
        }
    }
}

