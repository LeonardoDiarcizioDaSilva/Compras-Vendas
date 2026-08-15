package connectionFactory;

import connectionFactory.connectionExceptions.FailedToConnectExcepetion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    static Connection connection;

    private ConnectionFactory(Connection connection) {

    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed())
            return connection = initConection();
        return connection;
    }

    private static Connection initConection() {
        try {
           return DriverManager.getConnection("jdbc:postgresql://localhost:5432/commerce", "postgres", "admin");
        } catch (Exception e) {
            throw new FailedToConnectExcepetion();
        }
    }
}
