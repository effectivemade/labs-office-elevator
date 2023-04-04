package band.effective.office.tv.repository.synology

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.synology.PhotoDomain
import kotlinx.coroutines.flow.Flow

interface PhotoSynologyRepository {
    suspend fun getPhotosUrl(folderPath: String, sid: String):
            Flow<Either<String, List<PhotoDomain>>>
}

