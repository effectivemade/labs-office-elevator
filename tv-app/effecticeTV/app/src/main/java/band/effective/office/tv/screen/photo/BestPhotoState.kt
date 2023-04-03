package band.effective.office.tv.screen.photo

import band.effective.office.tv.screen.photo.model.Photo


data class BestPhotoState(
    val isSuccess: Boolean = true,
    val photos: List<Photo> = listOf(),
    val error: String = "",
    val isPlay: Boolean = true,
    val isLoading: Boolean = false
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