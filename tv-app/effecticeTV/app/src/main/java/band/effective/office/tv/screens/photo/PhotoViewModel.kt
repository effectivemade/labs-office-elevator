package band.effective.office.tv.screens.photo

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.repository.SynologyRepository
import band.effective.office.tv.screens.photo.model.Photo
import band.effective.office.tv.screens.photo.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: SynologyRepository
) : ViewModel() {

    private val mutableState = MutableStateFlow(BestPhotoState.Empty)
    val state = mutableState.asStateFlow()

    private val mutableEffect = MutableSharedFlow<BestPhotoEffect>()
    val effect = mutableEffect.asSharedFlow()

    /*This variable save time start timer */

    private var timerJob: Job? = null

    init {
        startTimer()
        viewModelScope.launch {
            repository.auth()
            updatePhoto()
        }
    }

    fun sendEvent(event: BestPhotoEvent) {
        when (event) {
            is BestPhotoEvent.OnClickNextItem -> {
                startTimer()
                viewModelScope.launch {
                    mutableEffect.emit(BestPhotoEffect.ScrollToItem(event.index + 1))
                }
            }
            is BestPhotoEvent.OnClickPreviousItem -> {
                startTimer()
                viewModelScope.launch {
                    mutableEffect.emit(BestPhotoEffect.ScrollToItem(event.index - 1))
                }
            }
            BestPhotoEvent.OnClickPlayButton -> {
                val isPlay = !mutableState.value.isPlay
                mutableState.update {
                    it.copy(isPlay = isPlay)
                }
                if (!isPlay) timerJob?.cancel()
                else {
                    startTimer()
                }
                viewModelScope.launch {
                    mutableEffect.emit(BestPhotoEffect.ChangePlayState(isPlay))
                }
            }
        }
    }

    private suspend fun updatePhoto() {
        repository.getPhotosUrl("\"${BuildConfig.folderPathPhotoSynology}\"").collect { result ->
            when (result) {
                is Resource.Error -> {
                    mutableState.update { state ->
                        state.copy(isSuccess = false, error = result.error)
                    }
                    Log.e("TAG", result.error)
                }
                is Resource.Data -> {
                    Log.d("href", result.data.toString())
                    mutableState.update { state ->
                        state.copy(photos = result.toUIModel(), isSuccess = true)
                    }
                }
            }
        }
    }
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (isActive && mutableState.value.isPlay){
                val startTime = System.currentTimeMillis()
                var currentTime = System.currentTimeMillis()
                while (isActive && currentTime - startTime < BuildConfig.slideShowPeriod * 1000)
                    currentTime = System.currentTimeMillis()
                if (isActive)
                    mutableEffect.emit(BestPhotoEffect.ScrollToNextItem)
            }
        }
    }
}