package band.effective.office.tv.network.use_cases

import band.effective.office.tv.repository.synology.AuthSynologyRepository
import javax.inject.Inject

class AuthSynologyUseCase @Inject constructor(
    private val authSynologyRepository: AuthSynologyRepository
) {
    suspend operator fun invoke() =
        authSynologyRepository.auth()
}