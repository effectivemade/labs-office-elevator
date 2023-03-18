package band.effective.office.tv.screens.photo.model

import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.model.domain.synology.PhotoDomain

data class Photo(
    val photoThumb: String
)

fun Resource.Data<List<PhotoDomain>>.toUIModel() = data.map { it.toUIModel() }

fun PhotoDomain.toUIModel() = Photo(
    photoThumb = photoThumb
)