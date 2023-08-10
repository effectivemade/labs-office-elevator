package band.effective.office.elevator.data.database

import band.effective.office.elevator.scheme.ProfileData

interface DBSource {

    fun getUser(idToken: String): ProfileData

    fun getAll(): List<ProfileData>

    fun update(profileData: ProfileData)
}