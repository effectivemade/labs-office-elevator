package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import com.arkivanov.decompose.ComponentContext

class RealEventLengthComponent(
    componentContext: ComponentContext,
    private val changeLength: (Int) -> Unit
) : ComponentContext by componentContext {
    fun increment() = changeLength(15)
    fun decrement() = changeLength(-30)
}