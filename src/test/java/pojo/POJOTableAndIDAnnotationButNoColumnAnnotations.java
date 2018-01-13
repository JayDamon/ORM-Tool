package pojo;

import libraries.orm.annotations.ID;
import libraries.orm.annotations.Table;
import libraries.orm.orm.Crudable;

@Table(
        name = "testTableName"
)
public class POJOTableAndIDAnnotationButNoColumnAnnotations implements Crudable {
    @ID
    private int id;

    public POJOTableAndIDAnnotationButNoColumnAnnotations() {
    }
}
