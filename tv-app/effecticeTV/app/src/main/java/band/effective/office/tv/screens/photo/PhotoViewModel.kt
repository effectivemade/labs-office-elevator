package band.effective.office.tv.screens.photo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.screens.photo.model.Photo
import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.repository.SynologyPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: SynologyPhoto
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
        when (val res = repository.getPhotosUrl("\"/BAND/Photos/Albums/Photo_March\"")) {
            is Resource.Error -> {
                _state.update { state ->
                    state.copy(isSuccess = false, error = res.error)
                }
                Log.e("TAG", res.error)
            }
            is Resource.Data -> {
                Log.d("href", res.data.toString())
                _state.update { state ->
                    state.copy(photos = res.data.map { Photo(it) }, isSuccess = true)
                }
            }
        }
    }
}