package libraries.orm.orm;

import libraries.orm.annotations.ColumnName;
import libraries.orm.annotations.DataTable;
import libraries.orm.annotations.ID;
import libraries.orm.annotations.TableName;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@DataTable
public class Table {
    private TableName tableName;
    private List<Column> columnList;
    private ID id;

    public Table(Crudable c) {
        this.setTableName(this.getTableNameFromObject(c));
        this.setColumnList(this.getColumnNameFields(c));
    }

    private TableName getTableNameFromObject(Crudable c) {
        if (c.getClass().isAnnotationPresent(TableName.class)) {
            return (TableName)c.getClass().getAnnotation(TableName.class);
        } else {
            throw new IllegalArgumentException(c.getClass().toString() + " does not have a 'TableName' Annotation");
        }
    }

    private List<Column> getColumnNameFields(Crudable c) {
        List<Column> names = new ArrayList();
        Field[] fields = c.getClass().getDeclaredFields();
        boolean idExists = false;
        boolean columnNameExists = false;

        for (Field f : fields) {
            if (f.isAnnotationPresent(ColumnName.class)) {
                this.addColumn(names, c, f);
                columnNameExists = true;
            } else if (f.isAnnotationPresent(ID.class)) {
                this.setId((ID) f.getAnnotation(ID.class));
                idExists = true;
            }
        }

        if (idExists && columnNameExists) {
            return names;
        } else if (!idExists && !columnNameExists) {
            throw new IllegalArgumentException(c.getClass().toString() + ": There is not ID or ColumnName field or they are annotated incorrectly");
        } else if (!idExists) {
            throw new IllegalArgumentException(c.getClass().toString() + ": ID Field does not exist or is not annotated properly");
        } else {
            throw new IllegalArgumentException(c.getClass().toString() + ": There is no ColumnName Field in class or is not annotated properly");
        }
    }

    private void addColumn(List<Column> names, Crudable c, Field f) {
        try {
            names.add(new Column(c, f));
        } catch (NoSuchMethodException var5) {
            throw new IllegalArgumentException(c.getClass().toString() + ": Getter method does not exist or it is not named properly", var5);
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
}
