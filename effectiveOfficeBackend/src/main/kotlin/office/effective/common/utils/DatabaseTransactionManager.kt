package office.effective.common.utils

import org.ktorm.database.Database
import org.ktorm.database.TransactionIsolation
import org.ktorm.database.TransactionManager

class DatabaseTransactionManager(database: Database) {
    private val transactionManager: TransactionManager
    init {
        transactionManager = database.transactionManager
    }

    fun <T> readCommitedTransaction(serviceCall: () -> T): T {
        val transaction = transactionManager.newTransaction(isolation = TransactionIsolation.READ_COMMITTED)

        val result: T
        try {
            result = serviceCall()
        } catch (e: Throwable) {
            transaction.rollback()
            throw e
        }
        transaction.commit()
        return result
    }
}
