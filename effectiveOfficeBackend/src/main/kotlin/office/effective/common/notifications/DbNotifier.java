package office.effective.common.notifications;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.impossibl.postgres.jdbc.PGDataSource;

import java.sql.Statement;

public class DbNotifier {
    private static final String host = System.getenv("DATABASE_HOST");
    private static final String port = System.getenv("DATABASE_PORT");
    private static final String databaseName = System.getenv("DATABASE_NAME");

    private static final String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, databaseName);
    private static final String username = System.getenv("DATABASE_USERNAME");
    private static final String password = System.getenv("DATABASE_PASSWORD");

    private static final NotificationSender sender = new NotificationSender();
    public static void listenToNotifyMessage() {

        PGDataSource dataSource = new PGDataSource();
        dataSource.setHost(host);
        dataSource.setPort(Integer.parseInt(port));
        dataSource.setDatabase(databaseName);
        dataSource.setUser(username);
        dataSource.setPassword(password);

        PGNotificationListener listener = (int processId, String channelName, String payload)
                -> System.out.println("notification = " + payload);

        try (PGConnection connection = (PGConnection) dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("LISTEN test");
            statement.close();
            connection.addNotificationListener(listener);
            // it only works if the connection is open. Therefore, we do an endless loop here.
            while (true) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
