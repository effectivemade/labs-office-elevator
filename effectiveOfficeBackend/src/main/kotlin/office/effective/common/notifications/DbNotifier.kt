package office.effective.common.notifications

import com.impossibl.postgres.api.jdbc.PGNotificationListener
import com.impossibl.postgres.jdbc.PGDataSource
import java.sql.Statement

class DbNotifier {
    private val host = System.getenv("DATABASE_HOST")
    private val port = System.getenv("DATABASE_PORT")
    private val databaseName = System.getenv("DATABASE_NAME")

    private val url = String.format("jdbc:postgresql://%s:%s/%s", host, port, databaseName)
    private val username = System.getenv("DATABASE_USERNAME")
    private val password = System.getenv("DATABASE_PASSWORD")

    private val sender = NotificationSender()

    fun listenToNotifyMessage() {
        val dataSource = PGDataSource()
        dataSource.host = host
        dataSource.port = port.toInt()
        dataSource.database = databaseName
        dataSource.user = username
        dataSource.password = password
        val listener =
            PGNotificationListener { processId: Int, channelName: String?, payload: String ->
                println(
                    "notification = $payload"
                )
            }
        try {
            (dataSource.connection as com.impossibl.postgres.api.jdbc.PGConnection).use { connection ->
                val statement: Statement = connection.createStatement()
                statement.execute("LISTEN change_notification")
                statement.close()
                connection.addNotificationListener(listener)
                // it only works if the connection is open. Therefore, we do an endless loop here.
                while (true) {
                    Thread.sleep(500)
                }
            }
        } catch (e: Exception) {
            System.err.println(e)
        }
    }
}