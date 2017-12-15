package libraries.orm.crud;

import libraries.orm.orm.Crudable;

public class Result {
    private String columnName;
    private Object result;

    public Result(String columnName, Object result) {
        this.columnName = columnName;
        this.result = result;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
