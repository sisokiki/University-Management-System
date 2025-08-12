package university.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// tells Java this resource can be auto-closed.
public class ConnectionForSystem implements AutoCloseable {

    Connection connection;
    Statement statement;

    ConnectionForSystem() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:///university_management_DB", "root", "sisokiki");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}