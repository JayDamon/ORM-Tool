package pojo;

import libraries.orm.annotations.ID;
import libraries.orm.annotations.TableName;
import libraries.orm.orm.Crudable;

@TableName(
        name = "testTableName"
)
public class POJOTableAndIDAnnotationButNoColumnAnnotations implements Crudable {
    @ID(
            idColumnName = "id"
    )
    private int id;

    public POJOTableAndIDAnnotationButNoColumnAnnotations() {
    }
}
