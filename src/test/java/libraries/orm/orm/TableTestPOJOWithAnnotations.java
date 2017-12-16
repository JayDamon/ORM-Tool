package libraries.orm.orm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;
import pojo.POJOWithData;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;

public class TableTestPOJOWithAnnotations {

    static private POJOWithAnnotations pojo;
    static private List<String> columnList;

    @BeforeAll
    static void setup() {
        pojo = POJOWithData.getPojoWithAnnotationsPrimary();

        columnList = new ArrayList<>();
        columnList.add("testString");
        columnList.add("testInt");
        columnList.add("testDouble");
        columnList.add("testDate");
    }

    @Test
    void tableReturnsListOfColumnNameStrings() {
        assertThat(Table.getColumnNameList(POJOWithAnnotations.class), is(columnList));
    }

    @Test
    public void getTableNameFromAnnotatedClass() {
        assertEquals("testTableName", Table.getTableName(POJOWithAnnotations.class).name());
    }

    @Test
    public void getIDFromAnnotatedClass() {
        assertEquals("id", Table.getIDColumn(POJOWithAnnotations.class).getColumnName().name());
    }

    @Test
    public void getIDValueFromTable() throws InvocationTargetException, IllegalAccessException {
        assertEquals(1, Table.getIDColumn(POJOWithAnnotations.class).getGetterMethod().invoke(pojo));
    }

    @Test
    public void objectHasColumnNameFields() {
        assertEquals(4, Table.getColumnNameList(POJOWithAnnotations.class).size());
    }

    @Test
    public void columnListNameExist() {
       for (Column c : Table.getColumns(POJOWithAnnotations.class)) {
            assertNotNull(c.getColumnName());
        }
    }

    @Test
    public void columnListGettersExist() {
        for (Column c : Table.getColumns(POJOWithAnnotations.class)) {
            assertNotNull(c.getGetterMethod());
        }
    }
}
