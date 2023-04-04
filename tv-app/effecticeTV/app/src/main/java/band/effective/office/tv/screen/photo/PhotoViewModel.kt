package band.effective.office.tv.screen.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.repository.SynologyRepository
import band.effective.office.tv.screen.photo.model.Photo
import band.effective.office.tv.screen.photo.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: SynologyRepository,
    private val slideShow: TimerSlideShow
) : ViewModel() {

    private val mutableState = MutableStateFlow(BestPhotoState.Empty)
    val state = mutableState.asStateFlow()

    private val mutableEffect = MutableSharedFlow<BestPhotoEffect>()
    val effect = mutableEffect.asSharedFlow()

    init {
        slideShow.init(
            scope = viewModelScope,
            callbackToEnd = { mutableEffect.emit(BestPhotoEffect.ScrollToNextItem) },
            isPlay = true
        )
        viewModelScope.launch {
            repository.auth()
            updatePhoto()
        }
    }

    fun sendEvent(event: BestPhotoEvent) {
        when (event) {
            is BestPhotoEvent.OnClickNextItem -> {
                slideShow.resetTimer()
                viewModelScope.launch {
                    mutableEffect.emit(BestPhotoEffect.ScrollToItem(event.index + 1))
                }
            }
            is BestPhotoEvent.OnClickPreviousItem -> {
                slideShow.resetTimer()
                viewModelScope.launch {
                    mutableEffect.emit(BestPhotoEffect.ScrollToItem(event.index - 1))
                }
            }
            BestPhotoEvent.OnClickPlayButton -> {
                val isPlay = !mutableState.value.isPlay
                mutableState.update {
                    it.copy(isPlay = isPlay)
                }
                if (!isPlay) slideShow.stopTimer()
                else {
                    slideShow.startTimer()
                }
                viewModelScope.launch {
                    mutableEffect.emit(BestPhotoEffect.ChangePlayState(isPlay))
                }
            }
        }
    }

    private suspend fun updatePhoto() {
        mutableState.update {state ->
            state.copy(isLoading = true)
        }

       repository.getPhotosUrl("\"${BuildConfig.folderPathPhotoSynology}\"").collect { result->
           when(result) {
               is Either.Failure -> {
                   mutableState.update { state ->
                       state.copy(isError = true, error = result.error)
                   }
               }
               is Either.Success -> {
                   mutableState.update { state ->
                       state.copy(photos = result.toUIModel(), isError = false, isLoading = false)
                   }
                   slideShow.startTimer()
               }
           }
        }
    }
}
