package libraries.orm.orm;

import libraries.orm.annotations.DataTable;
import java.util.Iterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.POJOEmpty;
import pojo.POJONoAnnotation;
import pojo.POJONoFieldAnnotations;
import pojo.POJOTableAndIDAnnotationButNoColumnAnnotations;
import pojo.POJOWithAnnotations;
import pojo.POJOWrongGetterNoIDAnnotation;

class TableTest {
    TableTest() {
    }

    @Test
    public void tableHasTableAnnotation() {
        Assertions.assertEquals(DataTable.class, ((DataTable)Table.class.getAnnotation(DataTable.class)).annotationType());
    }

    @Test
    public void getTableNameFromAnnotatedClass() {
        Assertions.assertEquals("testTableName", (new Table(new POJOWithAnnotations())).getTableName().name());
    }

    @Test
    public void getIDFromAnnotatedClass() {
        Assertions.assertEquals("id", (new Table(new POJOWithAnnotations())).getId().idColumnName());
    }

    @Test
    public void getIllegalArgumentExceptionWhenNoIdExists() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Table(new POJONoFieldAnnotations());
        });
    }

    @Test
    public void objectHasColumnNameFields() {
        Assertions.assertEquals(4, (new Table(new POJOWithAnnotations())).getColumnList().size());
    }

    @Test
    public void columnListNameExist() {
        Iterator var1 = (new Table(new POJOWithAnnotations())).getColumnList().iterator();

        while(var1.hasNext()) {
            Column c = (Column)var1.next();
            Assertions.assertNotNull(c.getColumnName());
        }

    }

    @Test
    public void columnListGettersExist() {
        Iterator var1 = (new Table(new POJOWithAnnotations())).getColumnList().iterator();

        while(var1.hasNext()) {
            Column c = (Column)var1.next();
            Assertions.assertNotNull(c.getGetterMethod());
        }

    }

    @Test
    public void objectWithNoAnnotationThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            (new Table(new POJONoAnnotation())).getTableName().name();
        });
    }

    @Test
    public void objectHasNoColumnNameFields() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Table(new POJONoAnnotation());
        });
    }

    @Test
    public void emptyObjectHasNoColumnNameFields() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Table(new POJOEmpty());
        });
    }

    @Test
    public void emptyObjectPOJOHasWrongGetters() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            (new Table(new POJOWrongGetterNoIDAnnotation())).getColumnList().size();
        });
    }

    @Test
    public void noFieldAnnotationsThowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Table(new POJOTableAndIDAnnotationButNoColumnAnnotations());
        });
    }

    @Test
    public void noIDAnnotationThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Table(new POJOWrongGetterNoIDAnnotation());
        });
    }
}
