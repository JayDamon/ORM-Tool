package libraries.orm.crud.relationaldatabase.databasemanagement;

import libraries.orm.crud.Crud;
import libraries.orm.crud.DatabaseManagement;
import libraries.orm.crud.relationaldatabase.RelationalDatabaseCrud;
import libraries.orm.crud.relationaldatabase.RelationalDatabaseManagement;
import libraries.orm.orm.Table;
import libraries.orm.utility.CreateTestDatabaseTable;
import libraries.orm.utility.H2DBConnectionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJODropTest;
import pojo.POJOWithAnnotations;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseMetadataTest {

    static Connection connection;

    @BeforeAll
    public static void setup() {
        connection = new H2DBConnectionTest().getDBConnection();
        CreateTestDatabaseTable.createDatabaseTable(connection);
    }

    @Test
    public void whenCheckForTable_tableExists() {
        assertTrue(DatabaseMetadata.tableExists(connection, "testTableName"));
    }

    @Test
    public void whenCheckForTable_tableDoesNotExists() {
        assertFalse(DatabaseMetadata.tableExists(connection, "FakeTable"));
    }

    @Test
    public void whenDropTable_tableDropped() {
        RelationalDatabaseManagement<POJODropTest> databaseManagement = new RelationalDatabaseManagement<>(POJODropTest.class, connection);
        assertTrue(databaseManagement.dropTable(new POJODropTest()));
        assertTrue(DatabaseMetadata.tableExists(connection, Table.getTableName(POJODropTest.class).name()));
    }
}