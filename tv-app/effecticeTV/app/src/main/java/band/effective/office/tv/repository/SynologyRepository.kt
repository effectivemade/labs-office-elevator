package band.effective.office.tv.repository

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.model.domain.synology.PhotoDomain
import band.effective.office.tv.network.synology.models.AuthModel
import kotlinx.coroutines.flow.Flow

interface SynologyRepository  {
    suspend fun auth(): Either<String, AuthModel>

    suspend fun getPhotosUrl(folderPath: String): Flow<Either<String, List<PhotoDomain>>>
}