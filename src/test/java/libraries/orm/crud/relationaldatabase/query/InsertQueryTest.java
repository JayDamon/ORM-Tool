package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.SetupTestQueryParameters;
import libraries.orm.orm.Table;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsertQueryTest extends SetupTestQueryParameters {
    @Test
    public void insertQueryStringCreatedFromInsertQueryObject() {
        assertEquals(
                "INSERT INTO testTableName (testString, testInt, testDouble, testDate) VALUES(?, ?, ?, ?)",
                new InsertQuery(
                        Table.getTableName(POJOWithAnnotations.class).name(),
                        columnNamesAndValues
                ).toString()
        );
    }
}
