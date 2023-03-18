package band.effective.office.tv.repository.impl

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.network.entity.ErrorReason
import band.effective.office.tv.core.network.entity.unpack
import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.network.synology.models.AuthModel
import band.effective.office.tv.network.synology.models.response.SynologyAuthResponse
import band.effective.office.tv.repository.AuthSynologyRepository
import javax.inject.Inject

class AuthSynologyRepositoryImpl @Inject constructor(
    private val synologyApi: SynologyApi
) : AuthSynologyRepository {

    override suspend fun auth(): Resource<AuthModel> {
        val res: Either<ErrorReason, SynologyAuthResponse> =
            synologyApi.auth(
                version = 3,
                method = "login",
                login = BuildConfig.synologyLogin,
                password = BuildConfig.synologyPassword,
            )

        return res.unpack(
            success = { response ->
                val sid = response.data?.sid ?: ""
                Resource.Data(AuthModel(sid = sid))
            },
            error = { error ->
                Resource.Error(error.message ?: "")
            }
        )
    }
}