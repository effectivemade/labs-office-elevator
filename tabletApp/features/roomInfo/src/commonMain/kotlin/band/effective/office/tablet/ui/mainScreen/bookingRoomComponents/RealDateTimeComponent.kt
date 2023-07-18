package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import com.arkivanov.decompose.ComponentContext

class RealDateTimeComponent(
    componentContext: ComponentContext,
    private val changeDay: (Int) -> Unit
) : ComponentContext by componentContext {
    fun incrementDay() = changeDay(1)
    fun decrementDay() = changeDay(-1)
}