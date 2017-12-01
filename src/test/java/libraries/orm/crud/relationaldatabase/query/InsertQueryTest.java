package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.SetupTestQueryParameters;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsertQueryTest extends SetupTestQueryParameters {
    @Test
    public void insertQueryStringCreatedFromInsertQueryObject() {
        assertEquals(
                "INSERT INTO testTableName (testString, testInt, testDouble, testDate) VALUES(?, ?, ?, ?)",
                new InsertQuery(
                        table.getTableName().name(),
                        columnNamesAndValues
                ).toString()
        );
    }
}
