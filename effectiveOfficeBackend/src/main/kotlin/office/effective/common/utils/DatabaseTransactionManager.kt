package office.effective.common.utils

import org.ktorm.database.Database
import org.ktorm.database.TransactionIsolation
import org.ktorm.database.TransactionManager
import org.slf4j.LoggerFactory

/**
 * Class used for creation database transaction on the facade layer
 */
class DatabaseTransactionManager(database: Database) {
    private val transactionManager: TransactionManager
    private val logger = LoggerFactory.getLogger(this::class.java)
    init {
        transactionManager = database.transactionManager
    }

    /**
     * Executes code in a database transaction.
     *
     * Rollbacks the transaction if an exception was thrown.
     *
     * @param serviceCall lambda function to be executed
     * @param isolation transaction isolation
     */
    fun <T> useTransaction(serviceCall: () -> T,
                           isolation: TransactionIsolation = TransactionIsolation.READ_COMMITTED): T {
        logger.debug("Opening new database {} transaction", isolation.name)
        val transaction = transactionManager.newTransaction(isolation = isolation)
        val result: T
        try {
            result = serviceCall()
            transaction.commit()
            logger.debug("Transaction committed")
        } catch (e: Throwable) {
            logger.debug("Rollback transaction")
            transaction.rollback()
            throw e
        } finally {
            transaction.close()
        }
        return result
    }
}
