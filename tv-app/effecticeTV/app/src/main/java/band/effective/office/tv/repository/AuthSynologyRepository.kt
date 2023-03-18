package band.effective.office.tv.repository

import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.network.synology.models.AuthModel

interface AuthSynologyRepository {
    suspend fun auth(): Resource<AuthModel>
}