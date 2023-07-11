package band.effective.office.tablet.ui.root

import band.effective.office.tablet.ui.freeNegotiationsScreen.FreeNegotiationsComponent
import band.effective.office.tablet.ui.mainScreen.mainScreen.MainComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
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
                    storeFactory = storeFactory
                )
            )
        }

        is Config.SelectRoom -> {
            Child.SelectRoomChild(FreeNegotiationsComponent(componentContext))
        }
    }

    sealed class Child {
        data class SelectRoomChild(val component: FreeNegotiationsComponent) : Child()
        data class MainChild(val component: MainComponent) : Child()
    }

    sealed class Config : Parcelable {

        @Parcelize
        object SelectRoom : Config()

        @Parcelize
        object Main : Config()
    }
}