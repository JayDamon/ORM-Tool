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
    static private Table table;

    @BeforeAll
    static void setup() {
        pojo = POJOWithData.getPojoWithAnnotationsPrimary();

        columnList = new ArrayList<>();
        columnList.add("testString");
        columnList.add("testInt");
        columnList.add("testDouble");
        columnList.add("testDate");

        table = new Table(pojo);
    }


    @Test
    void tableReturnsListOfColumnNameStrings() {
        assertThat(table.getColumnNameList(), is(columnList));
    }

    @Test
    public void getTableNameFromAnnotatedClass() {
        assertEquals("testTableName", (new Table(pojo)).getTableName().name());
    }

    @Test
    public void getIDFromAnnotatedClass() {
        assertEquals("id", (new Table(pojo)).getId().idColumnName());
    }

    @Test
    public void getIDValueFromTable() throws InvocationTargetException, IllegalAccessException {
        assertEquals(1, new Table(pojo).getIdColumn().getGetterMethod().invoke(pojo));
    }

    @Test
    public void objectHasColumnNameFields() {
        assertEquals(4, (new Table(new POJOWithAnnotations())).getColumnList().size());
    }

    @Test
    public void columnListNameExist() {
        Iterator var1 = (new Table(new POJOWithAnnotations())).getColumnList().iterator();

        while(var1.hasNext()) {
            Column c = (Column)var1.next();
            assertNotNull(c.getColumnName());
        }
    }

    @Test
    public void columnListGettersExist() {
        Iterator var1 = (new Table(new POJOWithAnnotations())).getColumnList().iterator();

        while(var1.hasNext()) {
            Column c = (Column)var1.next();
            assertNotNull(c.getGetterMethod());
        }

    }
}
