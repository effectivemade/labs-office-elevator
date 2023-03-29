package band.effective.office.tv.screens.photo

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
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
        slideShow.startTimer()

//        viewModelScope.launch {
//            repository.auth()
//            updatePhoto()
//        }

        mutableState.update {
            it.copy(
                photos = listOf(
                    Photo("https://media.istockphoto.com/id/1346125184/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%B3%D1%80%D1%83%D0%BF%D0%BF%D0%B0-%D1%83%D1%81%D0%BF%D0%B5%D1%88%D0%BD%D0%BE%D0%B9-%D0%BC%D0%BD%D0%BE%D0%B3%D0%BE%D0%BD%D0%B0%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D0%BE%D0%B9-%D0%B1%D0%B8%D0%B7%D0%BD%D0%B5%D1%81-%D0%BA%D0%BE%D0%BC%D0%B0%D0%BD%D0%B4%D1%8B.jpg?s=612x612&w=is&k=20&c=nbkK2hQxub07nehsvMxamrImCg_ptggazCFX8aC0nMg="),
                    Photo("https://media.istockphoto.com/id/635975374/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%BC%D1%8B-%D0%B2%D1%81%D0%B5%D0%B3%D0%B4%D0%B0-%D0%B4%D0%BE%D1%81%D1%82%D0%B8%D0%B3%D0%B0%D0%B5%D0%BC-%D0%BE%D0%BF%D1%82%D0%B8%D0%BC%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D1%85-%D1%80%D0%B5%D0%B7%D1%83%D0%BB%D1%8C%D1%82%D0%B0%D1%82%D0%BE%D0%B2-%D0%B2%D0%BC%D0%B5%D1%81%D1%82%D0%B5.jpg?s=612x612&w=is&k=20&c=47v_bNZIIvmlasx3UA8EqCInKt7fzwsL6iAFu7vPaUI="),
                    Photo("https://media.istockphoto.com/id/968943374/ru/%D1%84%D0%BE%D1%82%D0%BE/%D1%81%D1%82%D1%80%D0%B5%D0%BC%D0%B8%D1%82%D0%B5%D1%81%D1%8C-%D0%BA-%D1%83%D1%81%D0%BF%D0%B5%D1%85%D1%83-%D0%B8-%D0%B2%D1%8B-%D0%BA%D0%BE%D0%BC%D0%B0%D0%BD%D0%B4%D0%B0-%D0%B1%D1%83%D0%B4%D0%B5%D1%82-%D1%81%D0%BB%D0%B5%D0%B4%D0%BE%D0%B2%D0%B0%D1%82%D1%8C-%D1%8D%D1%82%D0%BE%D0%BC%D1%83-%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80%D1%83.jpg?s=612x612&w=is&k=20&c=j_FxgYm9EDhwnfPuwXi-02ROOa4xyM-PjwOluGnqiQU="),
                )
            )
        }
    }

    fun sendEvent(event: BestPhotoEvent) {
        when (event) {
            is BestPhotoEvent.OnClickNextItem -> {
                slideShow.resetTimer()
                viewModelScope.launch {
                    Log.d("INDEX", "index = ${event.index}")
                    mutableEffect.emit(BestPhotoEffect.ScrollToItem(event.index + 1))
                }
            }
            is BestPhotoEvent.OnClickPreviousItem -> {
                slideShow.resetTimer()
                viewModelScope.launch {
                    Log.d("INDEX", "index = ${event.index}")
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
                    Log.d("INDEX", "index = $isPlay")
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
//    private fun startTimer() {
//        timerJob?.cancel()
//        timerJob = viewModelScope.launch(Dispatchers.Default) {
//            while (isActive && mutableState.value.isPlay){
//                val startTime = System.currentTimeMillis()
//                var currentTime = System.currentTimeMillis()
//                while (isActive && currentTime - startTime < BuildConfig.slideShowPeriod * 1000)
//                    currentTime = System.currentTimeMillis()
//                if (isActive)
//                    mutableEffect.emit(BestPhotoEffect.ScrollToNextItem)
//            }
//        }
//    }
}