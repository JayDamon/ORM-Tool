package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.SetupTestQueryParameters;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelectQueryTest extends SetupTestQueryParameters {

    @Test
    public void selectAllColumnsQueryGeneratedFromSelectQueryObject() {
        assertEquals(
                "SELECT * FROM testTableName",
                new SelectQuery(
                        table.getTableName().name()
                ).toString()
        );
    }

    @Test
    public void selectQueryCreatedWithSpecificColumnName() {
        assertEquals(
                "SELECT testString FROM testTableName",
                new SelectQuery(
                        table.getTableName().name(),
                        table.getColumnNameList().get(0)
                ).toString()
        );
    }

    @Test
    public void selectQueryCreatedWithMulitpleColumnNames() {
        String[] args = new String[table.getColumnNameList().size()];
        args = table.getColumnNameList().toArray(args);
        assertEquals(
                "SELECT testString, testInt, testDouble, testDate FROM testTableName",
                new SelectQuery(
                        table.getTableName().name(),
                        args
                ).toString()
        );
    }

    @Test
    public void selectQueryCreatedWithMultipleColumnsAndWhereClause() throws InvocationTargetException, IllegalAccessException {
        String[] columnList = new String[table.getColumnNameList().size()];
        columnList = table.getColumnNameList().toArray(columnList);
        ArrayList<Condition> args = table.getColumnAndValueList();
        assertEquals(
                "SELECT testString, testInt, testDouble, testDate FROM testTableName " +
                        "WHERE testString = ? AND testInt = ? AND testDouble = ? AND testDate = ?",
                new SelectQuery(
                        table.getTableName().name(),
                        new WhereClause(args),
                        columnList
                ).toString()
        );
    }
}