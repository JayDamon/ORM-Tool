package libraries.orm.crud.relationaldatabase.databasemanagement;

import libraries.orm.crud.relationaldatabase.databasemanagement.DropTableQuery;
import libraries.orm.orm.Table;
import libraries.orm.utility.CreateTestDatabaseTable;
import libraries.orm.utility.H2DBConnectionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DropDeleteTableTest {

    static Connection connection;

    @BeforeAll
    public static void setup() {
        connection = new H2DBConnectionTest().getDBConnection();
        CreateTestDatabaseTable.createDatabaseTable(connection);
    }

    @Test
    public void whenWriteDropTableQuery_queryCreated() {
        String dropQuery = "DROP TABLE IF EXISTS testTableName;";
        assertEquals(dropQuery, new DropTableQuery(Table.getTableName(POJOWithAnnotations.class).name()).toString());
    }

    @Test
    public void whenDropTable_tableDropped() {

    }


}
