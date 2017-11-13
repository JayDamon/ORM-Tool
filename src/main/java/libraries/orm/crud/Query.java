package libraries.orm.crud;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import libraries.orm.orm.Column;
import libraries.orm.orm.Crudable;
import libraries.orm.orm.Table;

public abstract class Query {
    public Query() {
    }

    public PreparedStatement createQuery(Connection conn, Table table) {
        return PrepareStatement.preparedStatement(conn, this.createQueryString(table));
    }

    public void setParameters(Table table, PreparedStatement statement) throws SQLException {
        Class<?> clazz = table.getCrudable().getClass();
        try {
            Object newInstance = clazz.newInstance();
            setParameter(table, statement, newInstance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void setParameter(Table table, PreparedStatement statement, Object newInstance) throws IllegalAccessException, InvocationTargetException, SQLException {
        for (int i = 0 ; i < table.getColumnList().size() ; i++) {
            Column c = table.getColumnList().get(i);
            Object o = c.getGetterMethod().invoke(newInstance);
            if (o instanceof Integer) {
                setParameter(statement, i, (int)o);
            } else if (o instanceof String) {
                setParameter(statement, i, (String) o);
            } else if (o instanceof Short) {
                setParameter(statement, i, (short)o);
            } else if (o instanceof BigDecimal) {
                setParameter(statement, i, (BigDecimal) o);
            } else if (o instanceof Byte) {
                setParameter(statement, i, (byte)o);
            } else if (o instanceof Boolean) {
                setParameter(statement, i, (boolean)o);
            } else if (o instanceof Long) {
                setParameter(statement, i, (long)o);
            } else if (o instanceof Float) {
                setParameter(statement, i, (float)o);
            } else if (o instanceof Double) {
                setParameter(statement, i, (double)o);
            } else if (o instanceof Date) {
                setParameter(statement, i, (Date) o);
            }
        }
    }

    abstract String createQueryString(Table table);

    private void setParameter(PreparedStatement stmt, int parameterIndex, int value) throws SQLException {
        stmt.setInt(parameterIndex, value);
    }

    private void setParameter(PreparedStatement stmt, int parameterIndex, String value) throws SQLException {
        stmt.setString(parameterIndex, value);
    }

    private void setParameter(PreparedStatement stmt, int parameterIndex, short value) throws SQLException {
        stmt.setShort(parameterIndex, value);
    }

    private void setParameter(PreparedStatement stmt, int parameterIndex, BigDecimal value) throws SQLException {
        stmt.setBigDecimal(parameterIndex, value);
    }

    private void setParameter(PreparedStatement stmt, int parameterIndex, byte value) throws SQLException {
        stmt.setByte(parameterIndex, value);
    }

    private void setParameter(PreparedStatement stmt, int parameterIndex, boolean value) throws SQLException {
        stmt.setBoolean(parameterIndex, value);
    }

    private void setParameter(PreparedStatement stmt, int parameterIndex, long value) throws SQLException {
        stmt.setLong(parameterIndex, value);
    }

    private void setParameter(PreparedStatement stmt, int parameterIndex, float value) throws SQLException {
        stmt.setFloat(parameterIndex, value);
    }

    private void setParameter(PreparedStatement stmt, int parameterIndex, double value) throws SQLException {
        stmt.setDouble(parameterIndex, value);
    }

    private void setParameter(PreparedStatement stmt, int parameterIndex, Date value) throws SQLException {
        stmt.setDate(parameterIndex, value);
    }

}

