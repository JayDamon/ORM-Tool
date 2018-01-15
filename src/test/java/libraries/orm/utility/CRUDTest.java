package libraries.orm.utility;

import libraries.orm.crud.Condition;
import libraries.orm.crud.Crud;
import libraries.orm.crud.relationaldatabase.RelationalDatabaseCrud;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class CRUDTest {

    static Connection connection;
    private static POJOWithAnnotations pojo;
    static List<ArrayList<Condition>> expectedList;
    static ArrayList<Condition> expectedSelect;
    static POJOWithAnnotations pojoForEntryOne;
    static POJOWithAnnotations pojoNotExisting;
    static POJOWithAnnotations pojoForEntryTwo;

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

        pojoForEntryTwo = new POJOWithAnnotations();
        pojoForEntryTwo.setId(2);
        pojoForEntryTwo.setTestString("Test3");
        pojoForEntryTwo.setTestInt(53);
        pojoForEntryTwo.setTestDouble(53.33);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2017, Calendar.MAY, 33);
        pojoForEntryTwo.setTestDate(new Date(calendar3.getTimeInMillis()));

        pojoNotExisting = new POJOWithAnnotations();
        pojoNotExisting.setId(856248);
        pojoNotExisting.setTestString("Test");
        pojoNotExisting.setTestInt(5);
        pojoNotExisting.setTestDouble(5.22);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2017, Calendar.MAY, 20);
        pojoNotExisting.setTestDate(new Date(calendar1.getTimeInMillis()));

        expectedSelect = new ArrayList<>();
        expectedSelect.add(new Condition("ID", 1));
        expectedSelect.add(new Condition("TESTSTRING", "Test"));
        expectedSelect.add(new Condition("TESTINT", 5));
        expectedSelect.add(new Condition("TESTDOUBLE", 5.22));
        expectedSelect.add(new Condition("TESTDATE", new Date(calendar.getTimeInMillis())));

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

    @AfterAll
    public static void teardown() throws SQLException {
        connection.close();
    }

    @Test
    public void insertPojoIntoTestTable() throws SQLException {
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojo.getClass(), connection);
        assertTrue(crud.create(pojo));

        String sql = "SELECT * FROM testTableName " +
                "WHERE testString = 'TestValue' AND testInt = 20 AND testDouble = 30.0 AND testDate = '2017-11-05'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next());
    }

    @Test
    public void updatePojoInTestTable() throws SQLException {
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojo.getClass(), connection);
        assertTrue(crud.update(pojo));

        String sql = "SELECT * FROM testTableName " +
                "WHERE id = 1 AND testString = 'TestValue' " +
                "AND testInt = 20 AND testDouble = 30.0 AND testDate = '2017-11-05'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next());
    }

    @Test
    public void updatePojoInTestTableWithConditions() throws SQLException {
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojoForEntryTwo.getClass(), connection);
        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("testString", "Test2"));
        conditions.add(new Condition("testInt", 52));
        conditions.add(new Condition("testDouble", 52.22));
        assertTrue(crud.update(pojoForEntryTwo, conditions));

        String sql = "SELECT * FROM testTableName " +
                "WHERE id = 2 AND testString = 'Test3' " +
                "AND testInt = 53 AND testDouble = 53.33";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next());
    }

    @Test
    public void selectQueryReturnsSingleResult() {
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojoForEntryOne.getClass(), connection);
        List<POJOWithAnnotations> actualList = crud.read();
        assertThat(actualList, Matchers.not(IsEmptyCollection.empty()));
        assertThat(actualList, hasSize(2));
    }

    @Test
    public void selectQueryWithColumnsReturnsResult() {
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojoForEntryOne.getClass(), connection);
        List<POJOWithAnnotations> actualList = crud.read("testString", "testInt");
        assertThat(actualList, Matchers.not(IsEmptyCollection.empty()));
        assertEquals(0.0d, actualList.get(0).getTestDouble(), 5.22);
        assertEquals("Test", actualList.get(0).getTestString());
        assertEquals(5, actualList.get(0).getTestInt());
    }

    @Test
    public void selectQueryReturnsSingleResultWithConditions() {
        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("testString", "Test"));
        conditions.add(new Condition("testInt", 5));
        conditions.add(new Condition("testDouble", 5.22));
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojoForEntryOne.getClass(), connection);
        List<POJOWithAnnotations> actualList = crud.read(conditions);
        assertThat(actualList, Matchers.not(IsEmptyCollection.empty()));
        assertThat(actualList, hasSize(1));
    }

    @Test
    public void selectQueryReturnsSingleResultWithConditionsAndColumns() {
        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("testString", "Test"));
        conditions.add(new Condition("testInt", 5));
        conditions.add(new Condition("testDouble", 5.22));
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(POJOWithAnnotations.class, connection);
        List<POJOWithAnnotations> actualList = crud.read(conditions, "testString", "testInt");
        assertThat(actualList, Matchers.not(IsEmptyCollection.empty()));
        assertThat(actualList, hasSize(1));
        assertEquals("Test", actualList.get(0).getTestString());
        assertEquals(5, actualList.get(0).getTestInt());
    }

    @Test
    public void deleteQueryRemovesItem() throws SQLException {
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojoForEntryOne.getClass(), connection);
        assertTrue(crud.delete(pojoForEntryOne));

        String sql = "SELECT * FROM testTableName " +
                "WHERE id = 1 AND testString = 'TestValue' " +
                "AND testInt = 20 AND testDouble = 30.0 AND testDate = '2017-11-05'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        assertFalse(resultSet.next());
    }

    @Test
    public void pojoExistsInDatabase() throws InvocationTargetException, IllegalAccessException {
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojoForEntryOne.getClass(), connection);
        assertTrue(crud.exists(pojoForEntryOne));
    }

    @Test
    public void pojoExistsInDatabaseWithConditions() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("testString", "Test"));
        conditions.add(new Condition("testInt", 5));
        conditions.add(new Condition("testDouble", 5.22));
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojoForEntryOne.getClass(), connection);
        assertTrue(crud.exists(pojoForEntryOne,conditions));
    }

    @Test
    public void pojoDoesNotExistsInDatabaseWithConditions() {
        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("testString", "Test"));
        conditions.add(new Condition("testInt", 4));
        conditions.add(new Condition("testDouble", 5.22));
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojoForEntryOne.getClass(), connection);
        assertFalse(crud.exists(pojoForEntryOne,conditions));
    }

    @Test
    public void pojoDoesNotExistsInDatabase() throws InvocationTargetException, IllegalAccessException {
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojoNotExisting.getClass(), connection);
        assertFalse(crud.exists(pojoNotExisting));
    }

    @Test
    public void findPOJOByID_returnsPOJO() {
        Crud<POJOWithAnnotations, Integer> crud = new RelationalDatabaseCrud(pojoForEntryOne.getClass(), connection);
        assertEquals("Test", crud.findByID(1).getTestString());
    }
}
