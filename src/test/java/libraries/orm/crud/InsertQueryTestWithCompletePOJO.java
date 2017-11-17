package libraries.orm.crud;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Iterator;

import libraries.orm.orm.Column;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import libraries.orm.orm.Table;
import pojo.POJOWithAnnotations;

class InsertQueryTestWithCompletePOJO {
    static private POJOWithAnnotations pojo;

    InsertQueryTestWithCompletePOJO() {
    }

    @BeforeAll
    static void setup() {
        pojo = new POJOWithAnnotations();
        pojo.setTestString("TestValue");
        pojo.setTestInt(20);
        pojo.setTestDouble(30.0D);
        pojo.setTestDate(new Date(2017, 11, 5));
    }

    @Test
    public void createInsertStatement() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = InsertQuery.class.getDeclaredMethod("createQueryString", Table.class);
        method.setAccessible(true);
        Assert.assertEquals("INSERT INTO testTableName (testString, testInt, testDouble, testDate) VALUES(?, ?, ?, ?)", method.invoke(new InsertQuery(), new Table(pojo)));
    }

    @Test
    public void createUpdateStatement() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = UpdateQuery.class.getDeclaredMethod("createQueryString", Table.class);
        method.setAccessible(true);
        Assert.assertEquals("UPDATE testTableName SET testString = ?, testInt = ?, testDouble = ?, testDate = ? WHERE id = ?", method.invoke(new UpdateQuery(), new Table(pojo)));
    }

    @Test
    public void createDeleteStatement() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DeleteQuery.class.getDeclaredMethod("createQueryString", Table.class);
        method.setAccessible(true);
        Assert.assertEquals("DELETE FROM testTableName WHERE id = ?", method.invoke(new DeleteQuery(), new Table(pojo)));
    }

    @Test
    public void getterMethodsReturnValue() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Table table = new Table(pojo);
        for (int i = 0 ; i < table.getColumnList().size() ; i++) {
            Column c = table.getColumnList().get(i);
            Object o = c.getGetterMethod().invoke(table.getCrudable());
            System.out.println(o.toString());
        }
    }

}
