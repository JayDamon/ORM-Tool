package libraries.orm.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Jay Damon on 11/10/2017.
 */
public class DBConnection {
    private Connection connection;

    public void getDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection =
                DriverManager.getConnection("jdbc:mysql://localhost:6666/jcg", "root", "password");
    }

    public int executeQuery(String query) throws ClassNotFoundException, SQLException {
        return connection.createStatement().executeUpdate(query);
    }
}
