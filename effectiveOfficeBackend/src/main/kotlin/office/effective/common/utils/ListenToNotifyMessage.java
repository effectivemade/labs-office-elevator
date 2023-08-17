package office.effective.common.utils;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.impossibl.postgres.jdbc.PGDataSource;

import java.sql.Statement;

public class ListenToNotifyMessage {
    public static void listenToNotifyMessage() {
        PGDataSource dataSource = new PGDataSource();
        dataSource.setHost("localhost");
        dataSource.setPort(5432);
        dataSource.setDatabase("database_name");
        dataSource.setUser("postgres");
        dataSource.setPassword("password");

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
