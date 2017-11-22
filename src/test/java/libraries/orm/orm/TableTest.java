package libraries.orm.orm;

import libraries.orm.annotations.DataTable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pojo.POJOEmpty;
import pojo.POJONoAnnotation;
import pojo.POJONoFieldAnnotations;
import pojo.POJOTableAndIDAnnotationButNoColumnAnnotations;
import pojo.POJOWrongGetterNoIDAnnotation;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableTest {
    TableTest() {
    }

    @Test
    public void tableHasTableAnnotation() {
        Assertions.assertEquals(DataTable.class, ((DataTable)Table.class.getAnnotation(DataTable.class)).annotationType());
    }



    @Test
    public void getIllegalArgumentExceptionWhenNoIdExists() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Table(new POJONoFieldAnnotations());
        });
    }

    @Test
    public void objectWithNoAnnotationThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            (new Table(new POJONoAnnotation())).getTableName().name();
        });
    }

    @Test
    public void objectHasNoColumnNameFields() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Table(new POJONoAnnotation());
        });
    }

    @Test
    public void emptyObjectHasNoColumnNameFields() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Table(new POJOEmpty());
        });
    }

    @Test
    public void emptyObjectPOJOHasWrongGetters() {
        assertThrows(IllegalArgumentException.class, () -> {
            (new Table(new POJOWrongGetterNoIDAnnotation())).getColumnList().size();
        });
    }

    @Test
    public void noFieldAnnotationsThowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Table(new POJOTableAndIDAnnotationButNoColumnAnnotations());
        });
    }

    @Test
    public void noIDAnnotationThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Table(new POJOWrongGetterNoIDAnnotation());
        });
    }


}
