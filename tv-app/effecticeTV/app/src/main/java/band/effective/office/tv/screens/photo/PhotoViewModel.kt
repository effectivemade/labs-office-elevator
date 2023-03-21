package band.effective.office.tv.screens.photo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.repository.PhotoSynologyRepository
import band.effective.office.tv.repository.SynologyRepository
import band.effective.office.tv.screens.photo.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: SynologyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BestPhotoState.Empty)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.auth()
            updatePhoto()
        }
    }

    private suspend fun updatePhoto() {
       repository.getPhotosUrl("\"${BuildConfig.folderPathPhotoSynology}\"").collect { result->
           when(result) {
               is Resource.Error -> {
                   _state.update { state ->
                       state.copy(isSuccess = false, error = result.error)
                   }
                   Log.e("TAG", result.error)
               }
               is Resource.Data -> {
                   Log.d("href", result.data.toString())
                   _state.update { state ->
                       state.copy(photos = result.toUIModel(), isSuccess = true)
                   }
               }
           }
        }
    }
}