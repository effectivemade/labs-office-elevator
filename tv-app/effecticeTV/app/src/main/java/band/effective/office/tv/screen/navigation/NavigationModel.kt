package band.effective.office.tv.screen.navigation

enum class Screen {
    Main,
    BestPhoto,
    Events,
    History
}

data class NavigationModel(
    val screen: Screen,
    val title: String
)