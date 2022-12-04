package band.effective.office.elevator.android.theme

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import band.effective.office.elevator.common.compose.theme.OfficeElevator
import band.effective.office.elevator.common.compose.theme.OfficeElevatorTheme
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalNavigator

fun ComponentActivity.setupThemedNavigation(
    startScreen: String,
    vararg providers: ProvidedValue<*>,
    navigationGraph: RootComposeBuilder.() -> Unit
) {
    setContent {
        OfficeElevatorTheme {
            val rootController = RootComposeBuilder()
                .apply(navigationGraph).build()
            rootController.backgroundColor = OfficeElevator.color.primaryBackground
            rootController.setupWithActivity(this)

            CompositionLocalProvider(
                *providers,
                LocalRootController provides rootController
            ) {
                ModalNavigator {
                    Navigator(startScreen)
                }
            }
        }
    }
}