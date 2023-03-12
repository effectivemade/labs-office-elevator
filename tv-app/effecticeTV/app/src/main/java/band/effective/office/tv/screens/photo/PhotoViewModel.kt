package band.effective.office.tv.screens.photo

import androidx.lifecycle.ViewModel
import band.effective.office.tv.network.synology.SynologyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val SynologyApi: SynologyApi
) : ViewModel()  {

}