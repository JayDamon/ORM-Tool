package crud;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import orm.Table;
import pojo.POJOWithAnnotations;

class InsertQueryTestWithCompletePOJO {
    private POJOWithAnnotations pojo;

    InsertQueryTestWithCompletePOJO() {
    }

    @Before
    public void setup() {
        this.pojo = new POJOWithAnnotations();
        this.pojo.setTestString("TestValue");
        this.pojo.setTestInt(20);
        this.pojo.setTestDouble(30.0D);
        this.pojo.setTestDate(new Date(2017, 11, 5));
    }

    @Test
    public void createInsertStatement() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.setup();
        Method method = InsertQuery.class.getDeclaredMethod("createQueryString", Table.class);
        method.setAccessible(true);
        Assert.assertEquals("INSERT INTO testTableName (testString, testInt, testDouble, testDate) VALUES(?, ?, ?, ?)", method.invoke(new InsertQuery(), new Table(this.pojo)));
    }

    @Test
    public void createUpdateStatement() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.setup();
        Method method = UpdateQuery.class.getDeclaredMethod("createQueryString", Table.class);
        method.setAccessible(true);
        Assert.assertEquals("UPDATE testTableName SET testString = ?, testInt = ?, testDouble = ?, testDate = ? WHERE id = ?", method.invoke(new UpdateQuery(), new Table(this.pojo)));
    }

    @Test
    public void createDeleteStatement() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.setup();
        Method method = DeleteQuery.class.getDeclaredMethod("createQueryString", Table.class);
        method.setAccessible(true);
        Assert.assertEquals("DELETE FROM testTableName WHERE id = ?", method.invoke(new DeleteQuery(), new Table(this.pojo)));
    }
}
