package libraries.orm.crud.relationaldatabase.databasemanagement;

import libraries.orm.orm.Table;
import libraries.orm.utility.CreateTestDatabaseTable;
import libraries.orm.utility.H2DBConnectionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJOCreateTest;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateTableTest {
    static Connection connection;

    @BeforeAll
    public static void setup() {
        connection = new H2DBConnectionTest().getDBConnection();
        CreateTestDatabaseTable.createDatabaseTable(connection);
    }

    @Test
    public void whenWriteCreateTableQuery_queryCreated() {
        String createQuery = "CREATE TABLE testCreateTableName (" +
                "id int(11) NOT NULL AUTO_INCREMENT," +
                "testString varchar(50)," +
                "testInt int(10)," +
                "testDouble Double," +
                "testDate DATE," +
                "PRIMARY KEY (id)" +
                ");";
        assertEquals(createQuery, new CreateTableQuery(new POJOCreateTest()).toString());
    }

//    @Test
//    public void whenDropTable_tableDropped() {
//        RelationalDatabaseManagement<POJODropTest> databaseManagement = new RelationalDatabaseManagement<>(POJODropTest.class, connection);
//        assertTrue(DatabaseMetadata.tableExists(connection, Table.getTableName(POJODropTest.class).name()));
//        databaseManagement.dropTable(new POJODropTest());
//        assertFalse(DatabaseMetadata.tableExists(connection, Table.getTableName(POJODropTest.class).name()));
//    }
}
