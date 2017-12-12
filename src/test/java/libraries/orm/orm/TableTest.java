package libraries.orm.orm;

import libraries.orm.annotations.DataTable;
import libraries.orm.crud.Condition;
import org.hamcrest.Matchers;
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

    static private Table table;
    static private ArrayList<Condition> conditions;
    static private ArrayList<Condition> columnValues;

    @BeforeAll
    public static void setup() {
        POJOWithAnnotations pojo = POJOWithData.getPojoWithAnnotationsPrimary();
        pojo.setId(2);

        conditions = new ArrayList<>();
        conditions.add(new Condition("id", 2));

        columnValues = new ArrayList<>();
        columnValues.add(new Condition("testString","TestValue"));
        columnValues.add(new Condition("testInt",20));
        columnValues.add(new Condition("testDouble",30.0D));
        columnValues.add(new Condition("testDate",pojo.getTestDate()));

        table = new Table(pojo);
    }

    @Test
    public void tableHasTableAnnotation() {
        Assertions.assertEquals(
                DataTable.class,
                ((DataTable)Table.class.getAnnotation(DataTable.class)).annotationType()
        );
    }

    @Test
    public void getIllegalArgumentExceptionWhenNoIdExists() {
        assertThrows(IllegalArgumentException.class, () -> new Table(new POJONoFieldAnnotations()));
    }

    @Test
    public void objectWithNoAnnotationThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> (new Table(new POJONoAnnotation())).getTableName().name());
    }

    @Test
    public void objectHasNoColumnNameFields() {
        assertThrows(IllegalArgumentException.class, () -> new Table(new POJONoAnnotation()));
    }

    @Test
    public void emptyObjectHasNoColumnNameFields() {
        assertThrows(IllegalArgumentException.class, () -> new Table(new POJOEmpty()));
    }

    @Test
    public void emptyObjectPOJOHasWrongGetters() {
        assertThrows(IllegalArgumentException.class, () -> (
                new Table(new POJOWrongGetterNoIDAnnotation())).getColumnList().size()
        );
    }

    @Test
    public void noFieldAnnotationsThowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Table(new POJOTableAndIDAnnotationButNoColumnAnnotations())
        );
    }

    @Test
    public void noIDAnnotationThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Table(new POJOWrongGetterNoIDAnnotation()));
    }

    @Test
    public void conditionsAreCorrect() throws InvocationTargetException, IllegalAccessException {
        Assert.assertEquals(conditions.get(0).getCondition(), table.getIDColumnAndValue().get(0).getCondition());
        Assert.assertEquals(conditions.get(0).getColumnName(), table.getIDColumnAndValue().get(0).getColumnName());
    }

    @Test
    public void columnValuesAreCorrect() throws InvocationTargetException, IllegalAccessException {
        Assert.assertEquals(columnValues.get(0).getCondition(), table.getColumnAndValueList().get(0).getCondition());
        Assert.assertEquals(columnValues.get(0).getColumnName(), table.getColumnAndValueList().get(0).getColumnName());
        Assert.assertEquals(columnValues.get(1).getCondition(), table.getColumnAndValueList().get(1).getCondition());
        Assert.assertEquals(columnValues.get(1).getColumnName(), table.getColumnAndValueList().get(1).getColumnName());
        Assert.assertEquals(columnValues.get(2).getCondition(), table.getColumnAndValueList().get(2).getCondition());
        Assert.assertEquals(columnValues.get(2).getColumnName(), table.getColumnAndValueList().get(2).getColumnName());
        Assert.assertEquals(columnValues.get(3).getCondition(), table.getColumnAndValueList().get(3).getCondition());
        Assert.assertEquals(columnValues.get(3).getColumnName(), table.getColumnAndValueList().get(3).getColumnName());
    }
}
