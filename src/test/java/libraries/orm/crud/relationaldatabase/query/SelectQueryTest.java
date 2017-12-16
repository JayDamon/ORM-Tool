package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.SetupTestQueryParameters;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Table;
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
                        Table.getTableName(pojo.getClass()).name()
                ).toString()
        );
    }

    @Test
    public void selectQueryCreatedWithSpecificColumnName() {
        assertEquals(
                "SELECT testString FROM testTableName",
                new SelectQuery(
                        Table.getTableName(pojo.getClass()).name(),
                        Table.getColumnNameList(pojo.getClass()).get(0)
                ).toString()
        );
    }

    @Test
    public void selectQueryCreatedWithMulitpleColumnNames() {
        String[] args = new String[Table.getColumnNameList(pojo.getClass()).size()];
        args = Table.getColumnNameList(pojo.getClass()).toArray(args);
        assertEquals(
                "SELECT testString, testInt, testDouble, testDate FROM testTableName",
                new SelectQuery(
                        Table.getTableName(pojo.getClass()).name(),
                        args
                ).toString()
        );
    }

    @Test
    public void selectQueryCreatedWithMultipleColumnsAndWhereClause() throws InvocationTargetException, IllegalAccessException {
        String[] columnList = new String[Table.getColumnNameList(pojo.getClass()).size()];
        columnList = Table.getColumnNameList(pojo.getClass()).toArray(columnList);
        ArrayList<Condition> args = Table.getColumnAndValueList(pojo);
        assertEquals(
                "SELECT testString, testInt, testDouble, testDate FROM testTableName " +
                        "WHERE testString = ? AND testInt = ? AND testDouble = ? AND testDate = ?",
                new SelectQuery(
                        Table.getTableName(pojo.getClass()).name(),
                        new WhereClause(args),
                        columnList
                ).toString()
        );
    }
}