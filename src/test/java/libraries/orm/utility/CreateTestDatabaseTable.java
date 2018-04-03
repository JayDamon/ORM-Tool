package libraries.orm.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTestDatabaseTable {

    public static void createDatabaseTable(Connection connection) {
        dropTableIfExists(connection);
        dropDropTableIfExists(connection);
        dropCreateTableIfExists(connection);
        insertTable(connection);
        addEntry(connection);
        addEntryTwo(connection);
        addEntryThree(connection);
        insertTableToDrop(connection);
    }

    private static void dropCreateTableIfExists(Connection connection) {
        String dropQuery = "DROP TABLE IF EXISTS testCreateTableName;";
        try (
                PreparedStatement dropPreparedStatement = connection.prepareStatement(dropQuery);
        ) {
            dropPreparedStatement.executeUpdate();
            dropPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropDropTableIfExists(Connection connection) {
        String dropQuery = "DROP TABLE IF EXISTS testDropTableName;";
        try (
                PreparedStatement dropPreparedStatement = connection.prepareStatement(dropQuery);
        ) {
            dropPreparedStatement.executeUpdate();
            dropPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertTableToDrop(Connection connection) {
        String createQuery = "CREATE TABLE testDropTableName (" +
                "id int(11) NOT NULL AUTO_INCREMENT," +
                "testString varchar(50)," +
                "testInt int(10)," +
                "testDouble Double," +
                "testDate DATE," +
                "PRIMARY KEY (id)" +
                ");";

        try (
                PreparedStatement createPreparedStatement = connection.prepareStatement(createQuery);
        ) {
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertTable(Connection connection) {
        String createQuery = "CREATE TABLE testTableName (" +
                "id int(11) NOT NULL AUTO_INCREMENT," +
                "testString varchar(50)," +
                "testInt int(10)," +
                "testDouble Double," +
                "testDate DATE," +
                "PRIMARY KEY (id)" +
                ");";

        try (
                PreparedStatement createPreparedStatement = connection.prepareStatement(createQuery);
        ) {
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropTableIfExists(Connection connection) {
        String dropQuery = "DROP TABLE IF EXISTS testTableName;";
        try (
                PreparedStatement dropPreparedStatement = connection.prepareStatement(dropQuery);
        ) {
            dropPreparedStatement.executeUpdate();
            dropPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addEntry(Connection connection) {
        String insertQuery = "INSERT INTO testTableName(testString, testInt, testDouble, testDate) VALUES ('Test', 5, 5.22, '2017-5-20');";
        try (
                PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);
        ) {
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addEntryTwo(Connection connection) {
        String insertQuery = "INSERT INTO testTableName(testString, testInt, testDouble, testDate) VALUES ('Test2', 52, 52.22, '2017-5-22');";
        try (
                PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);
        ) {
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addEntryThree(Connection connection) {
        String insertQuery = "INSERT INTO testTableName(testString, testInt, testDouble, testDate) VALUES ('Test3', 53, 53.33, '2017-5-30');";
        try (
                PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);
        ) {
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
