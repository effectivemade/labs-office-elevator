package band.effective.office.tv.screens.photo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.repository.PhotoSynologyRepository
import band.effective.office.tv.repository.SynologyRepository
import band.effective.office.tv.screens.photo.model.Photo
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
    //        viewModelScope.launch {
//            repository.auth()
//            updatePhoto()
//        }

        _state.update {
            it.copy(
                photos = listOf(
                    Photo("https://media.istockphoto.com/id/1346125184/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%B3%D1%80%D1%83%D0%BF%D0%BF%D0%B0-%D1%83%D1%81%D0%BF%D0%B5%D1%88%D0%BD%D0%BE%D0%B9-%D0%BC%D0%BD%D0%BE%D0%B3%D0%BE%D0%BD%D0%B0%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D0%BE%D0%B9-%D0%B1%D0%B8%D0%B7%D0%BD%D0%B5%D1%81-%D0%BA%D0%BE%D0%BC%D0%B0%D0%BD%D0%B4%D1%8B.jpg?s=612x612&w=is&k=20&c=nbkK2hQxub07nehsvMxamrImCg_ptggazCFX8aC0nMg="),
                    Photo("https://media.istockphoto.com/id/635975374/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%BC%D1%8B-%D0%B2%D1%81%D0%B5%D0%B3%D0%B4%D0%B0-%D0%B4%D0%BE%D1%81%D1%82%D0%B8%D0%B3%D0%B0%D0%B5%D0%BC-%D0%BE%D0%BF%D1%82%D0%B8%D0%BC%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D1%85-%D1%80%D0%B5%D0%B7%D1%83%D0%BB%D1%8C%D1%82%D0%B0%D1%82%D0%BE%D0%B2-%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%B5.jpg?s=612x612&w=is&k=20&c=47v_bNZIIvmlasx3UA8EqCInKt7fzwsL6iAFu7vPaUI="),
                    Photo("https://media.istockphoto.com/id/968943374/ru/%D1%84%D0%BE%D1%82%D0%BE/%D1%81%D1%82%D1%80%D0%B5%D0%BC%D0%B8%D1%82%D0%B5%D1%81%D1%8C-%D0%BA-%D1%83%D1%81%D0%BF%D0%B5%D1%85%D1%83-%D0%B8-%D0%B2%D1%8B-%D0%BA%D0%BE%D0%BC%D0%B0%D0%BD%D0%B4%D0%B0-%D0%B1%D1%83%D0%B4%D0%B5%D1%82-%D1%81%D0%BB%D0%B5%D0%B4%D0%BE%D0%B2%D0%B0%D1%82%D1%8C-%D1%8D%D1%82%D0%BE%D0%BC%D1%83-%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80%D1%83.jpg?s=612x612&w=is&k=20&c=j_FxgYm9EDhwnfPuwXi-02ROOa4xyM-PjwOluGnqiQU="),
                    )
            )
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