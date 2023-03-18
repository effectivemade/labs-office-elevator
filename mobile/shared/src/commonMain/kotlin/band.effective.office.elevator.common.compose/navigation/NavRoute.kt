package band.effective.office.elevator.common.compose.navigation

sealed class NavRoute(val route: String) {

    object Login: NavRoute("login")

    object Main: NavRoute("main") {
        object Home: NavRoute("home")
        object Profile: NavRoute("profile")
    }
}