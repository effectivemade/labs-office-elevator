package band.effective.office.tv.network.use_cases

import band.effective.office.tv.repository.PhotoSynologyRepository
import javax.inject.Inject

class PhotoSynologyUseCase @Inject constructor(
    private val photoSynologyRepository: PhotoSynologyRepository
) {
    suspend operator fun invoke(folderPath: String, sid: String) =
        photoSynologyRepository.getPhotosUrl(
            folderPath = folderPath,
            sid = sid
        )
}