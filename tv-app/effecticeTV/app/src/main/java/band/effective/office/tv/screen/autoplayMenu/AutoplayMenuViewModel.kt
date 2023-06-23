package band.effective.office.tv.screen.autoplayMenu

import androidx.lifecycle.ViewModel
import band.effective.office.tv.screen.autoplayController.AutoplayController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AutoplayMenuViewModel @Inject constructor(val autoplayController: AutoplayController): ViewModel() {
}