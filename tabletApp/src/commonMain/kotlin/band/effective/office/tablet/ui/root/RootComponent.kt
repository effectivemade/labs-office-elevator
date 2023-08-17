package band.effective.office.tablet.ui.root

import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.FreeNegotiationsComponent
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.FreeNegotiationsComponentImpl
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store.BookingStore
import band.effective.office.tablet.ui.mainScreen.mainScreen.MainComponent
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainStore
import band.effective.office.tablet.ui.mainScreen.settingsComponents.SettingsComponent
import band.effective.office.tablet.ui.mainScreen.settingsComponents.SettingsComponentImpl
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.android.parcel.Parcelize

class RootComponent(componentContext: ComponentContext, private val storeFactory: StoreFactory) :
    ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    val childStack = childStack(
        source = navigation,
        initialConfiguration = Config.Main,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): Child = when (config) {

        is Config.Main -> {
            Child.MainChild(
                MainComponent(
                    componentContext = componentContext,
                    OnSelectOtherRoomRequest = {
                        navigation.push(Config.SelectRoom)
                    },
                    onSettings = {
                        navigation.replaceAll(Config.Settings)
                    },
                    storeFactory = storeFactory
                )
            )
        }

        is Config.SelectRoom -> {
            Child.SelectRoomChild(
                FreeNegotiationsComponentImpl(
                    componentContext = componentContext,
                    storeFactory = storeFactory,
                    onMainScreen = { reset: Boolean ->
                        navigation.pop()
                        (childStack.value.active.instance as Child.MainChild).component.bookingRoomComponent.sendIntent(
                            BookingStore.Intent.OnChangeIsActive(reset)
                        )
                    },
                    onBookingInfo = {
                        (childStack.value.backStack.last().instance as Child.MainChild).component
                            .bookingRoomComponent.getBooking()
                    }
                )
            )
        }

        is Config.Settings -> {
            Child.SettingsChild(
                SettingsComponentImpl(
                    componentContext = componentContext,
                    storeFactory = storeFactory,
                    onMainScreen = {
                        navigation.replaceAll(Config.Main)
                    },
                    onExitApp = {
                        /*(Margarita Djinjolia)
                         https://stackoverflow.com/a/21576676 */
                        android.os.Process.killProcess(android.os.Process.myPid())
                    }
                )
            )
        }
    }

    sealed class Child {
        data class SelectRoomChild(val component: FreeNegotiationsComponent) : Child()
        data class MainChild(val component: MainComponent) : Child()
        data class SettingsChild(val component: SettingsComponent) : Child()
    }

    sealed class Config : Parcelable {
        @Parcelize
        object Settings : Config()

        @Parcelize
        object SelectRoom : Config()

        @Parcelize
        object Main : Config()
    }
}