package libraries.orm.orm;

import libraries.orm.annotations.DataTable;
import libraries.orm.crud.Condition;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TableTest {

    static private ArrayList<Condition> conditions;
    static private ArrayList<Condition> columnValues;
    static private POJOWithAnnotations pojo;
    static private ArrayList<Condition> columnValuesWithID;

    @BeforeAll
    public static void setup() {
        pojo = POJOWithData.getPojoWithAnnotationsPrimary();
        pojo.setId(2);

        conditions = new ArrayList<>();
        conditions.add(new Condition("id", 2));

        columnValues = new ArrayList<>();
        columnValues.add(new Condition("testString","TestValue"));
        columnValues.add(new Condition("testInt",20));
        columnValues.add(new Condition("testDouble",30.0D));
        columnValues.add(new Condition("testDate", pojo.getTestDate()));

        columnValuesWithID = new ArrayList<>();
        columnValuesWithID.add(new Condition("id", 2));
        columnValuesWithID.add(new Condition("testString","TestValue"));
        columnValuesWithID.add(new Condition("testInt",20));
        columnValuesWithID.add(new Condition("testDouble",30.0D));
        columnValuesWithID.add(new Condition("testDate", pojo.getTestDate()));

    }

    @Test
    public void tableHasTableAnnotation() {
        Assertions.assertEquals(
                DataTable.class,
                ((DataTable)Table.class.getAnnotation(DataTable.class)).annotationType()
        );
    }

    @Test
    public void noTableNameAnnotationThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Table.getTableName(POJONoAnnotation.class));
    }

    @Test
    public void noColumnNameAnnotation() {
        assertThrows(IllegalArgumentException.class, () -> Table.getColumnNameList(POJONoAnnotation.class));
    }

    @Test
    public void noIDAnnotationThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Table.getIDColumn(POJONoAnnotation.class));
    }

    @Test
    public void noGetterMethodForIDThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Table.getIDColumn(POJOWrongGetters.class));
    }

    @Test
    public void improperlyNamedGetterMethodThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> (
                Table.getColumnAndValueList(new POJOWrongGetters())).size()
        );
    }

    @Test
    public void missingColumnNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Table.getColumnsWithID(POJOEmpty.class);
        });
    }

    @Test
    public void conditionsAreCorrect() throws InvocationTargetException, IllegalAccessException {
        Assert.assertEquals(conditions.get(0).getCondition(), 
                Table.getIDColumnAndValue(pojo).getCondition());
        Assert.assertEquals(conditions.get(0).getColumnName(), 
                Table.getIDColumnAndValue(pojo).getColumnName());
    }

    @Test
    public void columnValuesAreCorrect() throws InvocationTargetException, IllegalAccessException {
        Assert.assertEquals(columnValues.get(0).getCondition(), Table.getColumnAndValueList(pojo).get(0).getCondition());
        Assert.assertEquals(columnValues.get(0).getColumnName(), Table.getColumnAndValueList(pojo).get(0).getColumnName());
        Assert.assertEquals(columnValues.get(1).getCondition(), Table.getColumnAndValueList(pojo).get(1).getCondition());
        Assert.assertEquals(columnValues.get(1).getColumnName(), Table.getColumnAndValueList(pojo).get(1).getColumnName());
        Assert.assertEquals(columnValues.get(2).getCondition(), Table.getColumnAndValueList(pojo).get(2).getCondition());
        Assert.assertEquals(columnValues.get(2).getColumnName(), Table.getColumnAndValueList(pojo).get(2).getColumnName());
        Assert.assertEquals(columnValues.get(3).getCondition(), Table.getColumnAndValueList(pojo).get(3).getCondition());
        Assert.assertEquals(columnValues.get(3).getColumnName(), Table.getColumnAndValueList(pojo).get(3).getColumnName());
    }

    @Test
    public void columnValuesWithIDAreCorrect() throws InvocationTargetException, IllegalAccessException {
        Assert.assertEquals(columnValuesWithID.get(0).getCondition(), Table.getColumnAndValuesWithID(pojo).get(0).getCondition());
        Assert.assertEquals(columnValuesWithID.get(0).getColumnName(), Table.getColumnAndValuesWithID(pojo).get(0).getColumnName());
        Assert.assertEquals(columnValuesWithID.get(1).getCondition(), Table.getColumnAndValuesWithID(pojo).get(1).getCondition());
        Assert.assertEquals(columnValuesWithID.get(1).getColumnName(), Table.getColumnAndValuesWithID(pojo).get(1).getColumnName());
        Assert.assertEquals(columnValuesWithID.get(2).getCondition(), Table.getColumnAndValuesWithID(pojo).get(2).getCondition());
        Assert.assertEquals(columnValuesWithID.get(2).getColumnName(), Table.getColumnAndValuesWithID(pojo).get(2).getColumnName());
        Assert.assertEquals(columnValuesWithID.get(3).getCondition(), Table.getColumnAndValuesWithID(pojo).get(3).getCondition());
        Assert.assertEquals(columnValuesWithID.get(3).getColumnName(), Table.getColumnAndValuesWithID(pojo).get(3).getColumnName());
        Assert.assertEquals(columnValuesWithID.get(4).getCondition(), Table.getColumnAndValuesWithID(pojo).get(4).getCondition());
        Assert.assertEquals(columnValuesWithID.get(4).getColumnName(), Table.getColumnAndValuesWithID(pojo).get(4).getColumnName());
    }
}
