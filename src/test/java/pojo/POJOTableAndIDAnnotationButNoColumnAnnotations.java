package pojo;

import annotations.ID;
import annotations.TableName;
import orm.Crudable;

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
