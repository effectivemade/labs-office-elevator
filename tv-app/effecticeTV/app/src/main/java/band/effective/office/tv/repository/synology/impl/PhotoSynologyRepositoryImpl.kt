package band.effective.office.tv.repository.synology.impl

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.network.entity.ErrorReason
import band.effective.office.tv.core.network.entity.unpack
import band.effective.office.tv.domain.model.synology.PhotoDomain
import band.effective.office.tv.domain.model.synology.toDomain
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.network.synology.models.response.SynologyListResponse
import band.effective.office.tv.repository.synology.PhotoSynologyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoSynologyRepositoryImpl @Inject constructor(
    private val synologyApi: SynologyApi,
) : PhotoSynologyRepository {
    override suspend fun getPhotosUrl(
        folderPath: String,
        sid: String
    ): Flow<Either<String, List<PhotoDomain>>> =
        flow {
            val res: Either<ErrorReason, SynologyListResponse> =
                synologyApi.getFiles(
                    sid = sid,
                    version = 1,
                    method = "list",
                    folderPath = folderPath
                )
            emit(res.unpack(
                success = { response ->
                    val paths = response.toDomain(sid)
                    Either.Success(paths)
                },
                error = { error ->
                    Either.Failure(error.message)
                }
            )
            )
        }
}