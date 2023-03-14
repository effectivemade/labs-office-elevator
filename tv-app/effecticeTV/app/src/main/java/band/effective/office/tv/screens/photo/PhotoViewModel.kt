package band.effective.office.tv.screens.photo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.network.Resource
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.repository.impl.SynologyPhotoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: SynologyPhotoImpl
) : ViewModel()  {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.auth()
        }
    }
    fun test() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val res = repository.getPhotoPath("%22%2FBAND%2FPhotos%2FAlbums%2FPhoto_March%22")) {
                is Resource.Error -> Log.e("TAG", res.error)
                is Resource.Data -> Log.d("OK", res.data.toString())
                is Resource.Loading -> Log.d("LOAD", "load")
            }
        }
    }
}