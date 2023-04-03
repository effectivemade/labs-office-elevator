package band.effective.office.tv.repository

import band.effective.office.tv.domain.models.Employee.EmployeeInfoEntity
import band.effective.office.tv.source.BirthdayRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BirthdaysRepositoryImpl(
    private val birthdayRemoteDataSource: BirthdayRemoteDataSource
) {
    val latestBirthdays: Flow<List<EmployeeInfoEntity>> = flow {
        val employeesInfo = birthdayRemoteDataSource.fetchLatestBirthdays()
        val result = mutableListOf<EmployeeInfoEntity>()
        employeesInfo.map {
            result.add(
                EmployeeInfoEntity(
                    it.firstName ?: throw NullPointerException(),
                    it.startDate ?: throw NullPointerException(),
                    it.nextBirthdayDate ?: throw NullPointerException(),
                    it.photoUrl ?: throw NullPointerException()
                )
            )
        }
        emit(result)
    }
}
