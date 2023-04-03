package band.effective.office.tv.repository

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.model.domain.synology.PhotoDomain
import kotlinx.coroutines.flow.Flow

interface PhotoSynologyRepository {
    suspend fun getPhotosUrl(folderPath: String, sid: String):
            Flow<Either<String, List<PhotoDomain>>>
}

