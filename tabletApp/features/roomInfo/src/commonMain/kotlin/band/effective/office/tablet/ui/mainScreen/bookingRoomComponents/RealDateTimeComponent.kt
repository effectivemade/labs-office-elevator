package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import com.arkivanov.decompose.ComponentContext

class RealDateTimeComponent(
    componentContext: ComponentContext,
    private val changeDay: (Int) -> Unit,
    private val setDay: (Int) -> Unit,
    private val setMonth: (Int) -> Unit,
    private val changeTime: (Long) -> Unit,
) : ComponentContext by componentContext {
    fun incrementDay() = changeDay(1)
    fun decrementDay() = changeDay(-1)
    fun changeCurrentDay(newDay: Int) = setDay(newDay)
    fun changeCurrentMonth(newMonth: Int) = setMonth(newMonth)
}