package libraries.orm.crud.relationaldatabase.databasemanagement;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseMetadata {

    public static boolean tableExists(Connection connection, String tableName) {
        ResultSet rs = null;
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            rs = dbm.getTables(null, null, tableName.toUpperCase(), null);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
