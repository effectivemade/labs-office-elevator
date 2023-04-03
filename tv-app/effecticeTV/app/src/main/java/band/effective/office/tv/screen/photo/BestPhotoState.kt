package band.effective.office.tv.screen.photo

import band.effective.office.tv.screen.photo.model.Photo


data class BestPhotoState(
    val isSuccess: Boolean = false,
    val photos: List<Photo> = listOf(),
    val error: String = ""
){
    companion object {
        val Empty = BestPhotoState()
    }
}