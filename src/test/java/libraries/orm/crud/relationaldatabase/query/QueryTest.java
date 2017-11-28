package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.OrderByClause;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedHashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QueryTest {
    static private Table table;
    static private String[] columnNames;
    static private LinkedHashMap<String, Object> columnNamesAndValues;
    static private LinkedHashMap<String, Object> expectedConditions;
    static private LinkedHashMap<String, Object> expectedColumnNamesAndValues;

    @BeforeAll
    static void setup() throws InvocationTargetException, IllegalAccessException {
        POJOWithAnnotations pojo = new POJOWithAnnotations();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.NOVEMBER, 5);
        pojo.setId(2);
        pojo.setTestString("TestValue");
        pojo.setTestInt(20);
        pojo.setTestDouble(30.0D);
        pojo.setTestDate(new Date(calendar.getTimeInMillis()));

        table = new Table(pojo);
        columnNames = new String[table.getColumnNameList().size()];
        columnNames = table.getColumnNameList().toArray(columnNames);

        columnNamesAndValues = table.getColumnAndValueList();

        expectedConditions = new LinkedHashMap<>();
        expectedConditions.put("id", 2);

        expectedColumnNamesAndValues = new LinkedHashMap<>();
        expectedColumnNamesAndValues.put("testString", "TestValue");
        expectedColumnNamesAndValues.put("testInt", 20);
        expectedColumnNamesAndValues.put("testDouble", 30.0D);
        expectedColumnNamesAndValues.put("testDate", new Date(calendar.getTimeInMillis()));
    }

    @Test
    public void createInsertQuery() {
        assertEquals(
                "INSERT INTO testTableName (testString, testInt, testDouble, testDate) VALUES(?, ?, ?, ?)",
                new InsertQuery(
                        table.getTableName().name(),
                        columnNamesAndValues
                ).toString()
        );
    }

    @Test
    public void createUpdateQueryWithWhere() {
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
    public void createVanillaSelectQuery() {
        assertEquals(
                "SELECT * FROM testTableName",
                new SelectQuery(
                        table.getTableName().name()
                ).toString()
        );
    }

    @Test
    public void createSelectQueryWithOneColumn() {
        assertEquals(
                "SELECT testString FROM testTableName",
                new SelectQuery(
                        table.getTableName().name(),
                        table.getColumnNameList().get(0)
                ).toString()
        );
    }

    @Test
    public void createSelectQueryWithMultipleColumns() {
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
    public void createSelectQueryWithWhereClause() throws InvocationTargetException, IllegalAccessException {
        String[] columnList = new String[table.getColumnNameList().size()];
        columnList = table.getColumnNameList().toArray(columnList);
        LinkedHashMap<String, Object> args = table.getColumnAndValueList();
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

    @Test
    public void createVanillaDeleteQuery() {
        assertEquals(
                "DELETE FROM testTableName WHERE id = ?",
                new DeleteQuery(
                        table.getTableName().name(),
                        new WhereClause(
                                expectedConditions
                                )
                ).toString()
        );
    }

    @Test
    public void createOrderByClauseSingleCondition() throws InvocationTargetException, IllegalAccessException {
        LinkedHashMap<String, Object> tableMap = table.getColumnAndValueList();
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("testString", tableMap.get("testString"));
        assertEquals(
                " ORDER BY testString",
                new OrderByClause(
                        parameters
                        ).toString()
        );
    }

    @Test
    public void createOrderByClauseMultipleCondition() throws InvocationTargetException, IllegalAccessException {
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        LinkedHashMap<String, Object> newMap = table.getColumnAndValueList(); //ToDo Ensure this is testing the right thing
        linkedHashMap.put("testString", linkedHashMap.get("testString"));
        linkedHashMap.put("testInt", linkedHashMap.get("testInt"));
        assertEquals(
                " ORDER BY testString, testInt",
                new OrderByClause(
                        linkedHashMap
                ).toString()
        );
    }

    @Test
    public void whereClauseNotNull() {
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
    public void whereClauseFirstConditionIsOne() throws InvocationTargetException, IllegalAccessException {

        assertThat(
                new WhereClause(table.getIDColumnAndValue()).getConditions(),
                is(expectedConditions)
        );
    }

    @Test
    public void updateQueryHasProperConditions() throws InvocationTargetException, IllegalAccessException {
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