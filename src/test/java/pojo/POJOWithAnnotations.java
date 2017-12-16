package pojo;

import libraries.orm.annotations.ColumnName;
import libraries.orm.annotations.ID;
import libraries.orm.annotations.TableName;
import java.sql.Date;
import libraries.orm.orm.Crudable;

@TableName(name = "testTableName")
public class POJOWithAnnotations implements Crudable {
    @ID
    @ColumnName(name = "id")
    private int id;
    @ColumnName(name = "testString")
    private String testString;
    @ColumnName(name = "testInt")
    private int testInt;
    @ColumnName(name = "testDouble")
    private double testDouble;
    @ColumnName(name = "testDate")
    private Date testDate;

    public POJOWithAnnotations() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTestString() {
        return this.testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public int getTestInt() {
        return this.testInt;
    }

    public void setTestInt(int testInt) {
        this.testInt = testInt;
    }

    public double getTestDouble() {
        return this.testDouble;
    }

    public void setTestDouble(double testDouble) {
        this.testDouble = testDouble;
    }

    public Date getTestDate() {
        return this.testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }
}
