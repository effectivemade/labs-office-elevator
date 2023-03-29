package band.effective.office.tv.screens.photo

import androidx.tv.foundation.lazy.list.TvLazyListState
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import band.effective.office.tv.screens.photo.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


data class BestPhotoState(
    val isSuccess: Boolean = true,
    val photos: List<Photo> = listOf(),
    val error: String = "",
    val isPlay: Boolean = true,
){
    companion object {
        val Empty = BestPhotoState()
    }
}

sealed interface BestPhotoEvent {
    object OnClickPlayButton: BestPhotoEvent

    data class OnClickNextItem (val index: Int): BestPhotoEvent

    data class OnClickPreviousItem (val index: Int): BestPhotoEvent

}

sealed interface BestPhotoEffect {
    data class ScrollToItem(val index: Int): BestPhotoEffect

    data class ChangePlayState(val isPlay: Boolean): BestPhotoEffect

    object ScrollToNextItem: BestPhotoEffect
}