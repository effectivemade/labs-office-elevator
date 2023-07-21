package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RealEventOrganizerComponent(
    componentContext: ComponentContext,
    private val onSelectOrganizer: (String) -> Unit
) :
    ComponentContext by componentContext {
    private var mutableExpanded = MutableStateFlow(false)
    val expanded = mutableExpanded.asStateFlow()

    private var mutableSelectedItem = MutableStateFlow("")
    val selectedItem = mutableSelectedItem.asStateFlow()

    fun onExpandedChange() {
        mutableExpanded.update { !it }
    }

    fun onSelectItem(item: String) {
        mutableSelectedItem.update { item }
        mutableExpanded.update { false }
        onSelectOrganizer(item)
    }
}