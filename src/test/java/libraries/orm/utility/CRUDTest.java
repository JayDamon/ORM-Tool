package libraries.orm.utility;

import libraries.orm.crud.Crud;
import libraries.orm.crud.relationaldatabase.RelationalDatabaseCrud;
import libraries.orm.crud.relationaldatabase.exceptions.QueryException;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;
import pojo.POJOWithData;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class CRUDTest {

    static Connection connection;
    private static POJOWithAnnotations pojo;
    static List<Map<String, Object>> expectedList;
    static Map<String, Object> expectedSelect;
    static POJOWithAnnotations pojoForEntryOne;
    static POJOWithAnnotations pojoNotExisting;

    @BeforeAll
    public static void setup() {
        connection = new H2DBConnectionTest().getDBConnection();
        CreateTestDatabaseTable.createDatabaseTable(connection);
        pojoForEntryOne = new POJOWithAnnotations();
        pojoForEntryOne.setId(1);
        pojoForEntryOne.setTestString("Test");
        pojoForEntryOne.setTestInt(5);
        pojoForEntryOne.setTestDouble(5.22);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.MAY, 20);
        pojoForEntryOne.setTestDate(new Date(calendar.getTimeInMillis()));

        pojoNotExisting = new POJOWithAnnotations();
        pojoNotExisting.setId(1);
        pojoNotExisting.setTestString("Test");
        pojoNotExisting.setTestInt(5);
        pojoNotExisting.setTestDouble(5.22);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2017, Calendar.MAY, 20);
        pojoNotExisting.setTestDate(new Date(calendar1.getTimeInMillis()));

        expectedSelect = new HashMap<>();
        expectedSelect.put("ID", 1);
        expectedSelect.put("TESTSTRING", "Test");
        expectedSelect.put("TESTINT", 5);
        expectedSelect.put("TESTDOUBLE", 5.22);
        expectedSelect.put("TESTDATE", new Date(calendar.getTimeInMillis()));

        expectedList = new ArrayList<>();
        expectedList.add(expectedSelect);


        Calendar pojoWithCal = Calendar.getInstance();
        pojoWithCal.set(2017, Calendar.NOVEMBER, 5);

        pojo = POJOWithData.getPojoWithAnnotationsPrimary();
    }

    @BeforeEach
    public void setUp() {
        CreateTestDatabaseTable.createDatabaseTable(connection);
    }

    @Test
    public void insertPojoIntoTestTable() throws InvocationTargetException, IllegalAccessException, SQLException {
        Crud crud = new RelationalDatabaseCrud(pojo, connection);
        assertTrue(crud.create());

        String sql = "SELECT * FROM testTableName " +
                "WHERE testString = 'TestValue' AND testInt = 20 AND testDouble = 30.0 AND testDate = '2017-11-05'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next());
    }

    @Test
    public void updatePojoInTestTable() throws InvocationTargetException, IllegalAccessException, SQLException {
        Crud crud = new RelationalDatabaseCrud(pojo, connection);
        assertTrue(crud.update()); //ToDo need way to set values of where clause

        String sql = "SELECT * FROM testTableName " +
                "WHERE id = 1 AND testString = 'TestValue' " +
                "AND testInt = 20 AND testDouble = 30.0 AND testDate = '2017-11-05'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next());
    }

    @Test
    public void selectQueryReturnsSingleResult() {
        Crud<Connection> crud = new RelationalDatabaseCrud(pojoForEntryOne, connection);
        List<Map<String, Object>> actualList = crud.read();
        assertThat(actualList, Matchers.not(IsEmptyCollection.empty()));
        assertThat(actualList, hasSize(1));
    }

    @Test
    public void selectQueryReturnsSingleResultWithConditions() {
        LinkedHashMap<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("testString", "Test");
        conditions.put("testInt", 5);
        conditions.put("testDouble", 5.22);
        Crud<Connection> crud = new RelationalDatabaseCrud(pojoForEntryOne, connection);
        List<Map<String, Object>> actualList = crud.read(conditions);
        assertThat(actualList, Matchers.not(IsEmptyCollection.empty()));
        assertThat(actualList, hasSize(1));
    }

    @Test
    public void selectQueryReturnsSingleResultWithConditionsAndColumns() {
        LinkedHashMap<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("testString", "Test");
        conditions.put("testInt", 5);
        conditions.put("testDouble", 5.22);
        Crud<Connection> crud = new RelationalDatabaseCrud(pojoForEntryOne, connection);
        List<Map<String, Object>> actualList = crud.read(conditions, "testString", "testInt");
        assertThat(actualList, Matchers.not(IsEmptyCollection.empty()));
        assertThat(actualList, hasSize(1));
        assertTrue(actualList.get(0).containsKey("TESTSTRING"));
        assertTrue(actualList.get(0).containsKey("TESTINT"));
        assertFalse(actualList.get(0).containsKey("TESTDATE"));
    }

    @Test
    public void deleteQueryRemovesItem() throws SQLException, InvocationTargetException, IllegalAccessException {
        Crud<Connection> crud = new RelationalDatabaseCrud(pojoForEntryOne, connection);
        assertTrue(crud.delete());

        String sql = "SELECT * FROM testTableName " +
                "WHERE id = 1 AND testString = 'TestValue' " +
                "AND testInt = 20 AND testDouble = 30.0 AND testDate = '2017-11-05'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void pojoExistrsInDatabase() throws InvocationTargetException, IllegalAccessException {
        Crud crud = new RelationalDatabaseCrud(pojoForEntryOne, connection);
        assertTrue(crud.exists());
    }

    @Test
    public void pojoExistrsInDatabaseWithConditions() throws InvocationTargetException, IllegalAccessException {
        LinkedHashMap<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("testString", "Test");
        conditions.put("testInt", 5);
        conditions.put("testDouble", 5.22);
        Crud crud = new RelationalDatabaseCrud(pojoForEntryOne, connection);
        assertTrue(crud.exists(conditions));
    }

    @AfterAll
    public static void teardown() throws SQLException {
        connection.close();
    }
}
