package libraries.orm.orm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import libraries.orm.utility.StringUtility;

public class Column {
    private libraries.orm.annotations.Column column;
    private Method getterMethod;

    public <C extends Crudable> Column(Class<C> crudable, Field field) throws NoSuchMethodException {
        this.setColumn(field.getAnnotation(libraries.orm.annotations.Column.class));
        this.setGetterMethod(this.createGetterMethod(crudable, field));
    }

    public libraries.orm.annotations.Column getColumn() {
        return this.column;
    }

    public void setColumn(libraries.orm.annotations.Column column) {
        this.column = column;
    }

    public Method getGetterMethod() {
        return this.getterMethod;
    }

    public void setGetterMethod(Method getterMethod) {
        this.getterMethod = getterMethod;
    }

    public <C extends Crudable> Method createGetterMethod(Class<C> crudable, Field f) throws NoSuchMethodException {
        String fieldName = StringUtility.capitalizeFirstLetter(f.getName());
        String method;
        if (f.getType().equals(Boolean.TYPE)) {
            method = "is" + fieldName;
        } else {
            method = "get" + fieldName;
        }
        return crudable.getMethod(method);
    }
}
