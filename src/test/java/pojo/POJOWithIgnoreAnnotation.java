package pojo;

import libraries.orm.annotations.Ignore;
import libraries.orm.annotations.Table;
import java.util.Date;
import libraries.orm.orm.Crudable;

@Table(
        name = "testTableName"
)
public class POJOWithIgnoreAnnotation implements Crudable {
    private String testString;
    @Ignore
    private int testInt;
    private double testDouble;
    private Date testDate;

    public POJOWithIgnoreAnnotation() {
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
