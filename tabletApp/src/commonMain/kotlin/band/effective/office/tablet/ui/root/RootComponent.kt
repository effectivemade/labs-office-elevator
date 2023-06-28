package band.effective.office.tablet.ui.root

import band.effective.office.tablet.ui.mainScreen.MainComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.StateFlow

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
            Child.SelectRoomChild(SelectRoomComponent(componentContext))
        }
    }

    sealed class Child {
        data class SelectRoomChild(val component: SelectRoomComponent) : Child()
        data class MainChild(val component: MainComponent) : Child()
    }

    sealed class Config : Parcelable {

        @Parcelize
        object SelectRoom : Config()

        @Parcelize
        object Main : Config()
    }
}