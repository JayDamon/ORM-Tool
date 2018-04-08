package libraries.orm.crud.relationaldatabase.databasemanagement;

import libraries.orm.crud.relationaldatabase.RelationalDatabaseManagement;
import libraries.orm.orm.Table;
import libraries.orm.utility.CreateTestDatabaseTable;
import libraries.orm.utility.H2DBConnectionTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJOCreateTest;
import pojo.POJONoAnnotation;
import pojo.POJONoFieldAnnotations;
import pojo.POJOWithIgnoreAnnotation;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateTableTest {
    static Connection connection;

    @BeforeAll
    public static void setup() {
        connection = new H2DBConnectionTest().getDBConnection();
        CreateTestDatabaseTable.createDatabaseTable(connection);
    }

    @AfterAll
    public static void teardown() throws SQLException {
        connection.close();
    }

    @Test
    public void whenWriteCreateTableQuery_queryCreated() {
        String createQuery = "CREATE TABLE testCreateTableName (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "testString VARCHAR," +
                "testInt INT," +
                "testDouble FLOAT," +
                "testDate DATE," +
                "PRIMARY KEY(id)" +
                ");";
        assertEquals(createQuery, new CreateTableQuery(new POJOCreateTest()).toString());
    }

    @Test
    public void whenCreateTable_tableCreated() {
        RelationalDatabaseManagement<POJOCreateTest> databaseManagement = new RelationalDatabaseManagement<>(POJOCreateTest.class, connection);
        assertFalse(DatabaseMetadata.tableExists(connection, Table.getTableName(POJOCreateTest.class).name()));
        databaseManagement.createTable(new POJOCreateTest());
        assertTrue(DatabaseMetadata.tableExists(connection, Table.getTableName(POJOCreateTest.class).name()));
    }

    @Test
    public void whenWriteCreateTableQueryWithoutAnnotations_queryCreated() {
        String createQuery = "CREATE TABLE testTableName (" +
                "testString VARCHAR," +
                "testInt INT," +
                "testDouble FLOAT," +
                "testDate DATE" +
                ");";
        assertEquals(createQuery, new CreateTableQuery(new POJONoFieldAnnotations()).toString());
    }

    @Test
    public void whenWriteTableQuery_queryCreatedWithoutIgnoredFields() {
        String createQuery = "CREATE TABLE testTableName (" +
                "testString VARCHAR," +
                "testDouble FLOAT," +
                "testDate DATE" +
                ");";
        assertEquals(createQuery, new CreateTableQuery(new POJOWithIgnoreAnnotation()).toString());
    }
}
