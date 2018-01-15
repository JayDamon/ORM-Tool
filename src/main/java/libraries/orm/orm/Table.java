package libraries.orm.orm;

import libraries.orm.annotations.Column;
import libraries.orm.annotations.DataTable;
import libraries.orm.annotations.ID;
import libraries.orm.crud.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@DataTable
public class Table {

    public static <C extends Crudable> libraries.orm.annotations.Table getTableName(Class<C> crudable) {
        if (crudable.isAnnotationPresent(libraries.orm.annotations.Table.class)) {
            return crudable.getAnnotation(libraries.orm.annotations.Table.class);
        } else {
            throw new IllegalArgumentException(crudable.toString() + " does not have a 'Table' Annotation");
        }
    }

    public static <C extends Crudable> List<libraries.orm.orm.Column> getColumns(Class<C> crudable) {
        List<libraries.orm.orm.Column> names = new ArrayList<>();
        Field[] fields = crudable.getDeclaredFields();

        for (Field f : fields) {
            if (!f.isAnnotationPresent(ID.class) && f.isAnnotationPresent(Column.class)) {
                addColumnToList(names, crudable, f);
            }
        }
        if (names.size() > 0) {
            return names;
        } else {
            throw new IllegalArgumentException(crudable.toString() +
                    ": There is no Column Field in class or is not annotated properly");
        }
    }

    public static <C extends Crudable> List<libraries.orm.orm.Column>getColumnsWithID(Class<C> crudable) {
        List<libraries.orm.orm.Column> names = new ArrayList<>();
        Field[] fields = crudable.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(Column.class)) {
                addColumnToList(names, crudable, f);
            }
        }
        if (names.size() > 0) {
            return names;
        } else {
            throw new IllegalArgumentException(crudable.toString() +
                    ": There is no Column Field in class or is not annotated properly");
        }
    }

    private static <C extends Crudable> void addColumnToList(List<libraries.orm.orm.Column> names, Class<C> c, Field f) {
        try {
            names.add(new libraries.orm.orm.Column(c, f));
        } catch (NoSuchMethodException var5) {
            throw new IllegalArgumentException(c.toString() + ": Getter method does not exist or it is not named properly", var5);
        }
    }

    public static <C extends Crudable> List<String> getColumnNameList(Class<C> crudable) {
        List<String> columnNames = new ArrayList<>();
        for (libraries.orm.orm.Column c : getColumns(crudable)) {
            columnNames.add(c.getColumn().name());
        }
        return columnNames;
    }

    public static Condition getIDColumnAndValue(Crudable crudable) throws InvocationTargetException, IllegalAccessException {
        libraries.orm.orm.Column column = getIDColumn(crudable.getClass());
        Method getID = column.getGetterMethod();
        Condition idColumnAndValue = new Condition(
                        column.getColumn().name(),
                        getID.invoke(crudable)
                );
        return idColumnAndValue;
    }

    public static ArrayList<Condition> getColumnAndValueList(Crudable crudable) throws InvocationTargetException, IllegalAccessException {
        ArrayList<Condition> columnNameAndValueList = new ArrayList<>();
        for (libraries.orm.orm.Column c : getColumns(crudable.getClass())) {
            Method getter = c.getGetterMethod();
            columnNameAndValueList.add(
                    new Condition(
                            c.getColumn().name(),
                            getter.invoke(crudable)
                    )
            );
        }
        return columnNameAndValueList;
    }

    public static ArrayList<Condition> getColumnAndValuesWithID(Crudable crudable) throws InvocationTargetException, IllegalAccessException {
        ArrayList<Condition> columnNameAndValueList = new ArrayList<>();
        for (libraries.orm.orm.Column c : getColumnsWithID(crudable.getClass())) {
            Method getter = c.getGetterMethod();
            columnNameAndValueList.add(
                    new Condition(
                            c.getColumn().name(),
                            getter.invoke(crudable)
                    )
            );
        }
        return columnNameAndValueList;
    }

    public static <C extends Crudable> libraries.orm.orm.Column getIDColumn(Class<C> c) {
        try {
            for (Field f : c.getDeclaredFields()) {
                if (f.isAnnotationPresent(ID.class)) {
                    return new libraries.orm.orm.Column(c, f);
                }
            }
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(c.toString() + ": Getter method does not exist or it is not named properly", e);
        }
        throw new IllegalArgumentException(c.toString() + ": ID Column Does not exits");
    }
}
