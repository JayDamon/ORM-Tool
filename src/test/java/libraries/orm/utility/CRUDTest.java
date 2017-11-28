package libraries.orm.utility;

import libraries.orm.crud.Crud;
import libraries.orm.crud.relationaldatabase.RelationalDatabaseCrud;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class CRUDTest {

    static Connection connection;
    static private POJOWithAnnotations pojo;
//    static Table table;
//    static String[] columnNames;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = new H2DBConnectionTest().getDBConnection();
        CreateTestDatabaseTable.createDatabaseTable(connection);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.NOVEMBER, 5);

        pojo = new POJOWithAnnotations();
        pojo.setId(1);
        pojo.setTestString("TestValue");
        pojo.setTestInt(20);
        pojo.setTestDouble(30.0D);
        pojo.setTestDate(new Date(calendar.getTimeInMillis()));

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
    public void selectQueryReturnsProperValues() {
        POJOWithAnnotations pojoForEntryOne = new POJOWithAnnotations();
//        pojoForEntryOne.setId(1);
//        pojoForEntryOne.setTestString("Test");
//        pojoForEntryOne.setTestInt(5);
//        pojoForEntryOne.setTestDouble(5.22);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, Calendar.MAY, 20);
//        pojoForEntryOne.setTestDate(new Date(calendar.getTimeInMillis()));

        Map<String, Object> expectedSelect = new HashMap<>();
        expectedSelect.put("ID", 1);
        expectedSelect.put("TESTSTRING", "Test");
        expectedSelect.put("TESTINT", 5);
        expectedSelect.put("TESTDOUBLE", 5.22);
        expectedSelect.put("TESTDATE", new Date(calendar.getTimeInMillis()));

        List<Map<String, Object>> expectedList = new ArrayList<>();
        expectedList.add(expectedSelect);
        Crud crud = new RelationalDatabaseCrud(pojoForEntryOne, connection);
        assertThat(crud.read(), is(expectedList));
//        assertThat(crud.read(), containsInAnyOrder(expectedSelect));
//        assertEquals(crud.read(), expectedList);
    }

    @AfterAll
    public static void teardown() throws SQLException {
        connection.close();
    }
}
