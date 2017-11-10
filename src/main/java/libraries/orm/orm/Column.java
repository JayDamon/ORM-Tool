package libraries.orm.orm;

import libraries.orm.annotations.ColumnName;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import libraries.orm.utility.StringUtility;

public class Column {
    private ColumnName columnName;
    private Method getterMethod;

    public Column(Crudable object, Field field) throws NoSuchMethodException {
        this.setColumnName((ColumnName)field.getAnnotation(ColumnName.class));
        this.setGetterMethod(this.createGetterMethod(object, field));
    }

    public ColumnName getColumnName() {
        return this.columnName;
    }

    public void setColumnName(ColumnName columnName) {
        this.columnName = columnName;
    }

    public Method getGetterMethod() {
        return this.getterMethod;
    }

    public void setGetterMethod(Method getterMethod) {
        this.getterMethod = getterMethod;
    }

    public Method createGetterMethod(Crudable object, Field f) throws NoSuchMethodException {
        String fieldName = StringUtility.capitalizeFirstLetter(f.getName());
        String method;
        if (f.getType().equals(Boolean.TYPE)) {
            method = "is" + fieldName;
        } else {
            method = "get" + fieldName;
        }

        return object.getClass().getMethod(method);
    }
}
