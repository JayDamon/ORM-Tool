package libraries.orm.crud.relationaldatabase.databasemanagement;

import libraries.orm.annotations.Column;
import libraries.orm.annotations.ID;
import libraries.orm.annotations.Ignore;
import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.query.Query;
import libraries.orm.orm.Crudable;
import libraries.orm.orm.Table;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateTableQuery extends Query {

    public CreateTableQuery(Crudable crudable) {
        super(crudable);
    }

    @Override
    protected StringBuilder writeQuery(String tableName, ArrayList<Condition> conditionsAndValues) {
        StringBuilder builder = new StringBuilder("CREATE TABLE")
                .append(" ").append(tableName)
                .append(" (");
        addFields(builder);
        return builder;
    }

    private StringBuilder addFields(StringBuilder builder) {
        Field[] fields = getCrudable().getClass().getDeclaredFields();
        int runSize = fields.length;
        int i = 1;
        String primaryKey = "";
        for (Field f : fields) {
            if (!f.isAnnotationPresent(Ignore.class)) {
                if (f.isAnnotationPresent(Column.class)) {
                    Column column = f.getAnnotation(Column.class);
                    String name = column.name();
                    builder.append(name).append(" ").append(getFieldType(f));
                    if (column.isPrimaryKey()) primaryKey = "PRIMARY KEY(" + name + ")";
                    if (!column.nullable()) builder.append(" ").append("NOT NULL");
                    if (column.autoIncrement()) builder.append(" ").append("AUTO_INCREMENT");
                } else {
                    String name = f.getName();
                    builder.append(name).append(" ").append(getFieldType(f));
                }
                if (runSize != i) builder.append(",");
            } else {
                if (runSize == i) builder.deleteCharAt(builder.length() -1);
            }
            i++;
        }
        if (!primaryKey.equals("")) builder.append(",").append(primaryKey);
        builder.append(");");
        return builder;
    }

    private String getFieldType(Field field) {
        Class<?> type = field.getType();
        if (type == boolean.class || type == Boolean.class) {
            return "BIT";
        }
        else if (type == byte.class || type == Byte.class) {
            return "TINYINT";
        }
        else if (type == short.class || type == Short.class) {
            return "SMALLINT";
        }
        else if (type == long.class || type == Long.class) {
            return "BIGINT";
        }
        else if (type == float.class || type == Float.class) {
            return "REAL";
        }
        else if (type == int.class || type == Integer.class) {
            return "INT";
        }
        else if (type == double.class || type == Double.class) {
            return "FLOAT";
        }
        else if (type == String.class) {
            return "VARCHAR";
        }
        else if (type == BigDecimal.class) {
            return "DECIMAL";
        }
        else if (type == BigInteger.class) {
            return "BIGINT";
        } else if (type == java.sql.Date.class || type == java.util.Date.class) {
            return "DATE";
        } else if (type == java.sql.Time.class) {
            return "TIME";
        } else if (type == Timestamp.class || type == Calendar.class) {
            return "TIMESTAMP";
        }
        throw new IllegalArgumentException(type + " Is not an accepted field datatype");
    }
}
