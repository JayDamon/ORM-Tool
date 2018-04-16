package pojo;

import libraries.orm.annotations.Column;
import libraries.orm.annotations.ID;
import libraries.orm.annotations.Table;
import libraries.orm.orm.Crudable;

import java.util.Calendar;

@Table(name = "testTableName")
public class POJOWithCalendar implements Crudable {
    @ID
    @Column(name = "id")
    private int id;
    @Column(name = "testString")
    private String testString;
    @Column(name = "testInt")
    private int testInt;
    @Column(name = "testDouble")
    private double testDouble;
    @Column(name = "testDate")
    private Calendar testCalendar;

    public POJOWithCalendar() {
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

    public Calendar getTestCalendar() {
        return this.testCalendar;
    }

    public void setTestCalendar(Calendar testCalendar) {
        this.testCalendar = testCalendar;
    }
}
