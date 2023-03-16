package band.effective.office.tv.screens.photo

import band.effective.office.tv.screens.photo.model.Photo


data class BestPhotoState(
    val isSuccess: Boolean = false,
    val photos: List<Photo> = listOf(),
    val error: String = ""
){
    companion object {
        val Empty = BestPhotoState()
    }
}