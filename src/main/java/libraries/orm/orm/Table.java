package libraries.orm.orm;

import libraries.orm.annotations.ColumnName;
import libraries.orm.annotations.DataTable;
import libraries.orm.annotations.ID;
import libraries.orm.annotations.TableName;
import libraries.orm.crud.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@DataTable
public class Table {

    public static <C extends Crudable> TableName getTableName(Class<C> crudable) {
        if (crudable.isAnnotationPresent(TableName.class)) {
            return crudable.getAnnotation(TableName.class);
        } else {
            throw new IllegalArgumentException(crudable.toString() + " does not have a 'TableName' Annotation");
        }
    }

    public static <C extends Crudable> List<Column> getColumns(Class<C> crudable) {
        List<Column> names = new ArrayList<>();
        Field[] fields = crudable.getDeclaredFields();

        for (Field f : fields) {
            if (!f.isAnnotationPresent(ID.class) && f.isAnnotationPresent(ColumnName.class)) {
                addColumnToList(names, crudable, f);
            }
        }
        if (names.size() > 0) {
            return names;
        } else {
            throw new IllegalArgumentException(crudable.toString() +
                    ": There is no ColumnName Field in class or is not annotated properly");
        }
    }

    public static <C extends Crudable> List<Column>getColumnsWithID(Class<C> crudable) {
        List<Column> names = new ArrayList<>();
        Field[] fields = crudable.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(ColumnName.class)) {
                addColumnToList(names, crudable, f);
            }
        }
        if (names.size() > 0) {
            return names;
        } else {
            throw new IllegalArgumentException(crudable.toString() +
                    ": There is no ColumnName Field in class or is not annotated properly");
        }
    }

    private static <C extends Crudable> void addColumnToList(List<Column> names, Class<C> c, Field f) {
        try {
            names.add(new Column(c, f));
        } catch (NoSuchMethodException var5) {
            throw new IllegalArgumentException(c.toString() + ": Getter method does not exist or it is not named properly", var5);
        }
    }

    public static <C extends Crudable> List<String> getColumnNameList(Class<C> crudable) {
        List<String> columnNames = new ArrayList<>();
        for (Column c : getColumns(crudable)) {
            columnNames.add(c.getColumnName().name());
        }
        return columnNames;
    }

    public static ArrayList<Condition> getIDColumnAndValue(Crudable crudable) throws InvocationTargetException, IllegalAccessException {
        ArrayList<Condition> idColumnAndValue = new ArrayList<>();
        Column column = getIDColumn(crudable.getClass());
        Method getID = column.getGetterMethod();
        idColumnAndValue.add(
                new Condition(
                        column.getColumnName().name(),
                        getID.invoke(crudable)
                ));
        return idColumnAndValue;
    }

    public static ArrayList<Condition> getColumnAndValueList(Crudable crudable) throws InvocationTargetException, IllegalAccessException {
        ArrayList<Condition> columnNameAndValueList = new ArrayList<>();
        for (Column c : getColumns(crudable.getClass())) {
            Method getter = c.getGetterMethod();
            columnNameAndValueList.add(
                    new Condition(
                            c.getColumnName().name(),
                            getter.invoke(crudable)
                    )
            );
        }
        return columnNameAndValueList;
    }

    public static ArrayList<Condition> getColumnAndValuesWithID(Crudable crudable) throws InvocationTargetException, IllegalAccessException {
        ArrayList<Condition> columnNameAndValueList = new ArrayList<>();
        for (Column c : getColumnsWithID(crudable.getClass())) {
            Method getter = c.getGetterMethod();
            columnNameAndValueList.add(
                    new Condition(
                            c.getColumnName().name(),
                            getter.invoke(crudable)
                    )
            );
        }
        return columnNameAndValueList;
    }

    public static <C extends Crudable> Column getIDColumn(Class<C> c) {
        try {
            for (Field f : c.getDeclaredFields()) {
                if (f.isAnnotationPresent(ID.class)) {
                    return new Column(c, f);
                }
            }
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(c.toString() + ": Getter method does not exist or it is not named properly", e);
        }
        throw new IllegalArgumentException(c.toString() + ": ID Column Does not exits");
    }
}
