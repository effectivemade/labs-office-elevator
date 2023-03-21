package band.effective.office.tv.repository.impl

import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.model.domain.synology.PhotoDomain
import band.effective.office.tv.network.synology.models.AuthModel
import band.effective.office.tv.network.use_cases.AuthSynologyUseCase
import band.effective.office.tv.network.use_cases.PhotoSynologyUseCase
import band.effective.office.tv.repository.SynologyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SynologyRepositoryImpl @Inject constructor(
    private val loginSynology: AuthSynologyUseCase,
    private val photoSynology: PhotoSynologyUseCase
) : SynologyRepository {

    private var sid: String? = null

    override suspend fun auth(): Resource<AuthModel> =
        withContext(Dispatchers.IO) {
            when (val authModel = loginSynology()) {
                is Resource.Data -> {
                    sid = authModel.data.sid
                    authModel
                }
                is Resource.Error -> authModel
            }
        }

    override suspend fun getPhotosUrl(
        folderPath: String
    ): Flow<Resource<List<PhotoDomain>>> =
        withContext(Dispatchers.IO) {
            photoSynology(
                sid = sid?:"",
                folderPath = folderPath
            )
        }
}
