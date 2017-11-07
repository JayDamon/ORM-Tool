package pojo;

import annotations.ColumnName;
import annotations.TableName;
import orm.Crudable;

@TableName(
        name = "testTableName"
)
public class POJOWrongGetterNoIDAnnotation implements Crudable {
    @ColumnName(
            name = "testString"
    )
    private String testString;
    @ColumnName(
            name = "testBoolean"
    )
    private boolean testInt;

    public POJOWrongGetterNoIDAnnotation() {
    }

    public String testString() {
        return this.testString;
    }

    public void makeestString(String testString) {
        this.testString = testString;
    }

    public boolean getTestInt() {
        return this.testInt;
    }

    public void setTestInt(boolean testInt) {
        this.testInt = testInt;
    }
}
