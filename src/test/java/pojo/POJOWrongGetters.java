package pojo;

import libraries.orm.annotations.Column;
import libraries.orm.annotations.ID;
import libraries.orm.annotations.Table;
import libraries.orm.orm.Crudable;

@Table(
        name = "testTableName"
)
public class POJOWrongGetters implements Crudable {
    @ID
    @Column(name = "id")
    private int id;
    @Column(
            name = "testString"
    )
    private String testString;
    @Column(
            name = "testBoolean"
    )
    private boolean testInt;

    public POJOWrongGetters() {
    }

    public int getTheId() {
        return id;
    }

    public void setTheId(int id) {
        this.id = id;
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
