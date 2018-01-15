package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.SetupTestQueryParameters;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Table;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;
import pojo.POJOWithData;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

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
                        Table.getTableName(POJOWithAnnotations.class).name(),
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
                        Table.getTableName(POJOWithAnnotations.class).name(),
                        new WhereClause(
                                expectedConditions
                        ),
                        columnNamesAndValues
                ).getWhereClause()
        );
    }

    @Test
    public void udpateQueryCreatedWithWhereClauseHasExpectedColumns() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Condition> list =  new UpdateQuery(
                Table.getTableName(POJOWithAnnotations.class).name(),
                new WhereClause(
                        new ArrayList<>(
                                Collections.singletonList(
                                        Table.getIDColumnAndValue(POJOWithData.getPojoWithAnnotationsPrimary())
                                ))),
                columnNamesAndValues).getColumnNameAndValueList();
        assertEquals(list.get(0).getCondition(),expectedColumnNamesAndValues.get(0).getCondition());
        assertEquals(list.get(0).getColumnName(),expectedColumnNamesAndValues.get(0).getColumnName());
        assertEquals(list.get(1).getCondition(),expectedColumnNamesAndValues.get(1).getCondition());
        assertEquals(list.get(1).getColumnName(),expectedColumnNamesAndValues.get(1).getColumnName());
        assertEquals(list.get(2).getCondition(),expectedColumnNamesAndValues.get(2).getCondition());
        assertEquals(list.get(2).getColumnName(),expectedColumnNamesAndValues.get(2).getColumnName());
        assertEquals(list.get(3).getCondition(),expectedColumnNamesAndValues.get(3).getCondition());
        assertEquals(list.get(3).getColumnName(),expectedColumnNamesAndValues.get(3).getColumnName());
    }
}
