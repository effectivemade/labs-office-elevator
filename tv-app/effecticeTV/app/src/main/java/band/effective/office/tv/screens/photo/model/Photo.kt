package band.effective.office.tv.screens.photo.model

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.model.domain.synology.PhotoDomain

data class Photo(
    val photoThumb: String
)

fun Either.Success<List<PhotoDomain>>.toUIModel() = data.map { it.toUIModel() }

fun PhotoDomain.toUIModel() = Photo(
    photoThumb = photoThumb
)