package libraries.orm.utility;

import libraries.orm.crud.Crud;
import libraries.orm.crud.relationaldatabase.RelationalDatabaseCrud;
import libraries.orm.crud.relationaldatabase.query.InsertQuery;
import libraries.orm.orm.Table;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Calendar;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        String sql = "SELECT * FROM testTableName WHERE testString = 'Test'";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            System.out.println(rs.getInt("id"));
        } else {
            System.out.println("Couldn't Find it");
        }
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
    public void updatePojoInTestTable() throws InvocationTargetException, IllegalAccessException {
        Crud crud = new RelationalDatabaseCrud(pojo, connection);
        assertTrue(crud.update()); //ToDo need way to set values of where clause
    }

    @AfterAll
    public static void teardown() throws SQLException {
        connection.close();
    }
}
