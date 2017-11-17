package libraries.orm.orm;

import libraries.orm.annotations.ColumnName;
import libraries.orm.annotations.DataTable;
import libraries.orm.annotations.ID;
import libraries.orm.annotations.TableName;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@DataTable
public class Table {
    private TableName tableName;
    private List<Column> columnList;
    private ID id;
    private Crudable crudable;
    private Column idColumn;

    public Table(Crudable crudable) {
        this.setCrudable(crudable);
        this.setTableName(this.getTableNameFromObject());
        this.setColumnList(this.getColumnNameFields());
    }

    private TableName getTableNameFromObject() {
        if (crudable.getClass().isAnnotationPresent(TableName.class)) {
            return crudable.getClass().getAnnotation(TableName.class);
        } else {
            throw new IllegalArgumentException(crudable.getClass().toString() + " does not have a 'TableName' Annotation");
        }
    }

    private List<Column> getColumnNameFields() {
        List<Column> names = new ArrayList();
        Field[] fields = crudable.getClass().getDeclaredFields();
        boolean idExists = false;
        boolean columnNameExists = false;

        for (Field f : fields) {
            if (f.isAnnotationPresent(ColumnName.class)) {
                this.addColumn(names, crudable, f);
                columnNameExists = true;
            } else if (f.isAnnotationPresent(ID.class)) {
                this.setId(f.getAnnotation(ID.class));
                this.setIdColumn(createColumn(f));
                idExists = true;
            }
        }

        if (idExists && columnNameExists) {
            return names;
        } else if (!idExists && !columnNameExists) {
            throw new IllegalArgumentException(crudable.getClass().toString() + ": There is not ID or ColumnName field or they are annotated incorrectly");
        } else if (!idExists) {
            throw new IllegalArgumentException(crudable.getClass().toString() + ": ID Field does not exist or is not annotated properly");
        } else {
            throw new IllegalArgumentException(crudable.getClass().toString() + ": There is no ColumnName Field in class or is not annotated properly");
        }
    }

    private void addColumn(List<Column> names, Crudable c, Field f) {
        try {
            names.add(new Column(c, f));
        } catch (NoSuchMethodException var5) {
            throw new IllegalArgumentException(c.getClass().toString() + ": Getter method does not exist or it is not named properly", var5);
        }
    }

    private Column createColumn(Field field) {
        try {
            return new Column(crudable, field);
        } catch (NoSuchMethodException var5) {
            throw new IllegalArgumentException(crudable.getClass().toString() + ": Getter method does not exist or it is not named properly", var5);
        }
    }

    public TableName getTableName() {
        return this.tableName;
    }

    public void setTableName(TableName tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumnList() {
        return this.columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public ID getId() {
        return this.id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Crudable getCrudable() {
        return crudable;
    }

    public void setCrudable(Crudable crudable) {
        this.crudable = crudable;
    }

    public Column getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(Column idColumn) {
        this.idColumn = idColumn;
    }
}
