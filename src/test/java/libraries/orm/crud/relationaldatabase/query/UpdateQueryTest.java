package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.SetupTestQueryParameters;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateQueryTest extends SetupTestQueryParameters {

    @Test
    public void updateQueryGeneratedFromUpdateQueryObject() {
        assertEquals(
                "UPDATE testTableName " +
                        "SET testString = ?, testInt = ?, testDouble = ?, testDate = ? WHERE id = ?",
                new UpdateQuery(
                        table.getTableName().name(),
                        new WhereClause(
                                expectedConditions
                        ),
                        columnNamesAndValues
                ).toString()
        );
    }

    @Test
    public void whereClauseContainedInUpdateQueryObect() {
        assertNotNull(
                new UpdateQuery(
                        table.getTableName().name(),
                        new WhereClause(
                                expectedConditions
                        ),
                        columnNamesAndValues
                ).getWhereClause()
        );
    }

    @Test
    public void udpateQueryCreatedWithWhereClauseHasExpectedColumns() throws InvocationTargetException, IllegalAccessException {
        assertThat(
                new UpdateQuery(
                        table.getTableName().name(),
                        new WhereClause(table.getIDColumnAndValue()),
                        columnNamesAndValues
                ).getColumnNameAndValueList(),
                is(expectedColumnNamesAndValues)
        );
    }
}
