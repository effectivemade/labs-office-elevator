package band.effective.office.tv.repository.impl

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.network.entity.ErrorReason
import band.effective.office.tv.core.network.entity.unpack
import band.effective.office.tv.model.domain.synology.PhotoDomain
import band.effective.office.tv.model.domain.synology.toDomain
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.network.synology.models.response.SynologyAuthResponse
import band.effective.office.tv.network.synology.models.response.SynologyListResponse
import band.effective.office.tv.repository.PhotoSynologyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoSynologyRepositoryImpl @Inject constructor(
    private val synologyApi: SynologyApi,
) : PhotoSynologyRepository {
    override suspend fun getPhotosUrl(
        folderPath: String,
        sid: String
    ): Flow<Resource<List<PhotoDomain>>> =
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
                    Resource.Data(paths)
                },
                error = { error ->
                    Resource.Error(error.message ?: "")
                }
            )
            )
        }
}