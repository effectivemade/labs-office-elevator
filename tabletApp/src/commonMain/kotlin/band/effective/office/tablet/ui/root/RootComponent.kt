package band.effective.office.tablet.ui.root

import band.effective.office.tablet.ui.mainScreen.MainComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.android.parcel.Parcelize
import tablet.domain.model.Booking
import tablet.domain.model.EventInfo
import tablet.ui.selectRoomScreen.RealSelectRoomComponent
import java.util.Calendar

class RootComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

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
            Child.MainChild(MainComponent(
                componentContext =  componentContext,
                onClick = {
                    navigation.push(Config.SelectRoom)
                }
            ))
        }

        is Config.SelectRoom -> {
            Child.SelectRoomChild(
                RealSelectRoomComponent(
                    componentContext,
                    Booking
                        ("Sirius",
                        EventInfo
                            (
                            Calendar.getInstance(),
                            Calendar.getInstance(),
                            "Ольга Белозёрова"
                        )
                    )
                )
            )
        }
    }

    sealed class Child {
        data class SelectRoomChild(val component: RealSelectRoomComponent) : Child()
        data class MainChild(val component: MainComponent) : Child()
    }

    sealed class Config : Parcelable {

        @Parcelize
        object SelectRoom : Config()

        @Parcelize
        object Main : Config()
    }
}