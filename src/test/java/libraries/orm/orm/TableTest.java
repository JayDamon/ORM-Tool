package libraries.orm.orm;

import libraries.orm.annotations.DataTable;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.clauses.Clause;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Assert.assertThat(table.getIDColumnAndValue(), is(conditions));
    }

    @Test
    public void columnValuesAreCorrect() throws InvocationTargetException, IllegalAccessException {
        Assert.assertThat(table.getColumnAndValueList(), is(columnValues));
    }
}
