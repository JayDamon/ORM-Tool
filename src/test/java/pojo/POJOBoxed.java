package pojo;

import libraries.orm.annotations.Column;
import libraries.orm.annotations.ID;
import libraries.orm.annotations.Table;
import libraries.orm.orm.Crudable;

import java.sql.Date;

@Table(name = "testTableName")
public class POJOBoxed implements Crudable {
    @ID
    @Column(name = "id")
    private Integer id;
    @Column(name = "testString")
    private String testString;
    @Column(name = "testInt")
    private Integer testInt;
    @Column(name = "testDouble")
    private Double testDouble;
    @Column(name = "testDate")
    private Date testDate;

    public POJOBoxed() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public Integer getTestInt() {
        return testInt;
    }

    public void setTestInt(Integer testInt) {
        this.testInt = testInt;
    }

    public Double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(Double testDouble) {
        this.testDouble = testDouble;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }
}
