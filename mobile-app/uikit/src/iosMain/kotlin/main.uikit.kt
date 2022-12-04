import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import kotlinx.cinterop.*
import platform.Foundation.NSStringFromClass
import platform.UIKit.*
import band.effective.office.elevator.common.compose.NavigationTree
import band.effective.office.elevator.common.compose.navigation.navigationGraph
import band.effective.office.elevator.common.compose.theme.OfficeElevator
import band.effective.office.elevator.common.compose.theme.OfficeElevatorTheme
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator

fun main() {
    val args = emptyArray<String>()
    memScoped {
        val argc = args.size + 1
        val argv = (arrayOf("skikoApp") + args).map { it.cstr.ptr }.toCValues()
        autoreleasepool {
            UIApplicationMain(argc, argv, null, NSStringFromClass(SkikoAppDelegate))
        }
    }
}

class SkikoAppDelegate : UIResponder, UIApplicationDelegateProtocol {
    companion object : UIResponderMeta(), UIApplicationDelegateProtocolMeta

    @ObjCObjectBase.OverrideInit
    constructor() : super()

    private var _window: UIWindow? = null
    override fun window() = _window
    override fun setWindow(window: UIWindow?) {
        _window = window
    }

    override fun application(
        application: UIApplication,
        didFinishLaunchingWithOptions: Map<Any?, *>?
    ): Boolean {
        window = UIWindow(frame = UIScreen.mainScreen.bounds)
        window!!.rootViewController = Application("Odyssey Demo") {
            OfficeElevatorTheme {
                Column(modifier = Modifier.background(OfficeElevator.color.primaryBackground)) {
                    // To skip upper part of screen.
                    Box(
                        modifier = Modifier.height(56.dp)
                    )

                    val rootController = RootComposeBuilder().apply { navigationGraph() }.build()
                    rootController.backgroundColor = OfficeElevator.color.primaryBackground

                    CompositionLocalProvider(
                        LocalRootController provides rootController
                    ) {
                        ModalNavigator {
                            Navigator(NavigationTree.Actions.name)
                        }
                    }
                }
            }
        }
        window!!.makeKeyAndVisible()
        return true
    }
}
