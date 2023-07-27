package band.effective.office.elevator.data.database

import band.effective.office.elevator.Database

class DBSourceImpl(
    val database: Database
): DBSource {

    private val profileQueries = database.profileQueries

    override fun getProfileName(): String {
        return profileQueries.selectAll().executeAsOne().full_name
    }
}