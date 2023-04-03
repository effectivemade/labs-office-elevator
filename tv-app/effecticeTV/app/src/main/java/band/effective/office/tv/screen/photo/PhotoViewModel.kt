package band.effective.office.tv.screen.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.repository.SynologyRepository
import band.effective.office.tv.screen.photo.model.toUIModel
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

    private val mutableState = MutableStateFlow(BestPhotoState.Empty)
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.auth()
            updatePhoto()
        }
    }

    private suspend fun updatePhoto() {
       repository.getPhotosUrl("\"${BuildConfig.folderPathPhotoSynology}\"").collect { result->
           when(result) {
               is Either.Failure -> {
                   mutableState.update { state ->
                       state.copy(isSuccess = false, error = result.error)
                   }
               }
               is Either.Success -> {
                   mutableState.update { state ->
                       state.copy(photos = result.toUIModel(), isSuccess = true)
                   }
               }
           }
        }
    }
}