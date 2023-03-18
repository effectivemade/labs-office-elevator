package band.effective.office.elevator.common.compose.navigation.bottom

import band.effective.office.elevator.common.compose.navigation.NavRoute

enum class BottomNavItems(val linkedRoute: NavRoute, val label: String, val imageResource: String) {
    Elevator(linkedRoute = NavRoute.Main.Home, label = "Home", imageResource = "home"),
    Profile(linkedRoute = NavRoute.Main.Profile, label = "Profile", imageResource = "profile")
}