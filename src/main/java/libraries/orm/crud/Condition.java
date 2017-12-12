package libraries.orm.crud;

public class Condition {

    private String columnName;
    private Object condition;

    public Condition(String columnName, Object condition) {
        this.columnName = columnName;
        this.condition = condition;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getCondition() {
        return condition;
    }
}
