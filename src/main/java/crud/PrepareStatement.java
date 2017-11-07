package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrepareStatement {
    public PrepareStatement() {
    }

    public static PreparedStatement preparedStatement(Connection conn, String sql) {
        try (
                PreparedStatement stmt = conn.prepareStatement(sql)
                ) {
            return stmt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
