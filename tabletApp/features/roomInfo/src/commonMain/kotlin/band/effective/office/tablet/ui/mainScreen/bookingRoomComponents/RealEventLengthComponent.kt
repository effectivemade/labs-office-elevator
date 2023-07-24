package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import com.arkivanov.decompose.ComponentContext

class RealEventLengthComponent(
    componentContext: ComponentContext,
    private val changeLength: (Int) -> Unit
) : ComponentContext by componentContext {
    fun increment() = changeLength(30)
    fun decrement() = changeLength(-15)
}