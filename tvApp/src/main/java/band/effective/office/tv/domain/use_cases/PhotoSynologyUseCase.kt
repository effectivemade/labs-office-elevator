package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.repository.synology.PhotoSynologyRepository
import javax.inject.Inject

class PhotoSynologyUseCase @Inject constructor(
    private val photoSynologyRepository: PhotoSynologyRepository
) {
    suspend operator fun invoke(sid: String) =
        photoSynologyRepository.getPhotosUrl(sid = sid)
}