package libraries.orm.crud;

import libraries.orm.crud.relationaldatabase.preparedstatement.DeleteStatement;
import libraries.orm.crud.relationaldatabase.preparedstatement.InsertStatement;
import libraries.orm.crud.relationaldatabase.preparedstatement.ORMPreparedStatement;
import libraries.orm.crud.relationaldatabase.preparedstatement.UpdateStatement;
import libraries.orm.crud.relationaldatabase.query.InsertQuery;
import libraries.orm.orm.Column;
import libraries.orm.orm.Table;
import libraries.orm.utility.CreateTestDatabaseTable;
import libraries.orm.utility.H2DBConnectionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class InsertStatementTestWithCompletePOJO {
    static private POJOWithAnnotations pojo;
    static Connection connection;
    static final String INSERT_SQL = "INSERT INTO testTableName (testString, testInt, testDouble, testDate) VALUES(?, ?, ?, ?)";
    static final String UPDATE_SQL = "UPDATE testTableName SET testString = ?, testInt = ?, testDouble = ?, testDate = ? WHERE id = ?";
    static final String DELETE_SQL = "DELETE FROM testTableName WHERE id = ?";
    static Table table;
    static String[] columnNames;

    @BeforeAll
    static void setup() throws SQLException {
        pojo = new POJOWithAnnotations();
        pojo.setTestString("TestValue");
        pojo.setTestInt(20);
        pojo.setTestDouble(30.0D);
        pojo.setTestDate(new Date(2017, 11, 5));

        table = new Table(pojo);
        columnNames = new String[table.getColumnNameList().size()];
        columnNames = table.getColumnNameList().toArray(columnNames);
    }

    @Test
    public void createInsertStatement()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = InsertStatement.class.getDeclaredMethod("createQueryString", Table.class);
        method.setAccessible(true);
        assertEquals(
                INSERT_SQL,
                method.invoke(new InsertStatement(), new Table(pojo))
        );
    }


    @Test
    public void createUpdateStatement()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = UpdateStatement.class.getDeclaredMethod("createQueryString", Table.class);
        method.setAccessible(true);
        String query = method.invoke(new UpdateStatement(), new Table(pojo)).toString();
        System.out.println(query);
        assertEquals(
                UPDATE_SQL,
                query
        );
    }

    @Test
    public void createDeleteStatement()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DeleteStatement.class.getDeclaredMethod("createQueryString", Table.class);
        method.setAccessible(true);
        assertEquals(
                DELETE_SQL,
                method.invoke(new DeleteStatement(), new Table(pojo))
        );
    }

    @Test
    public void getterMethodsReturnValue()
            throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Table table = new Table(pojo);
        for (int i = 0 ; i < table.getColumnList().size() ; i++) {
            Column c = table.getColumnList().get(i);
            Object o = c.getGetterMethod().invoke(table.getCrudable());
            System.out.println(o.toString());
        }
    }

}
