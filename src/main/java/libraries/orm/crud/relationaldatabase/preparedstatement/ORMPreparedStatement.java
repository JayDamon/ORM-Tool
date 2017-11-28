package libraries.orm.crud.relationaldatabase.preparedstatement;

import libraries.orm.orm.Column;
import libraries.orm.orm.Table;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ORMPreparedStatement {
    public ORMPreparedStatement() {
    }

//    public PreparedStatement createQuery(Connection conn, Table table)
//            throws SQLException, InvocationTargetException, IllegalAccessException
//    {
//        PreparedStatement statement = conn.prepareStatement(this.createQueryString(table));
//        setParameters(table, statement);
//        return statement;
//    }

    public void setParameters(LinkedHashMap<String, Object> parameterList, PreparedStatement statement)
            throws SQLException, InvocationTargetException, IllegalAccessException
    {
        int i = 1;
        for (Map.Entry<String, Object> entry : parameterList.entrySet()) {
            setParameterBasedOnType(statement, i, entry.getValue());
            i++;
        }
//        for (int i = 0 ; i < table.getColumnList().size() ; i++) {
//            Column c = table.getColumnList().get(i);
//            Object o = c.getGetterMethod().invoke(table.getCrudable());
//            setParameterBasedOnType(statement, i + 1, o);
//        }
    }

    protected void setParameterBasedOnType(PreparedStatement statement, int i, Object o)
            throws SQLException {
        if (o instanceof Integer) {
            setStatementParameter(statement, i, (int)o);
        } else if (o instanceof String) {
            setStatementParameter(statement, i, (String) o);
        } else if (o instanceof Short) {
            setStatementParameter(statement, i, (short)o);
        } else if (o instanceof BigDecimal) {
            setStatementParameter(statement, i, (BigDecimal)o);
        } else if (o instanceof Byte) {
            setStatementParameter(statement, i, (byte)o);
        } else if (o instanceof Boolean) {
            setStatementParameter(statement, i, (boolean)o);
        } else if (o instanceof Long) {
            setStatementParameter(statement, i, (long)o);
        } else if (o instanceof Float) {
            setStatementParameter(statement, i, (float)o);
        } else if (o instanceof Double) {
            setStatementParameter(statement, i, (double)o);
        } else if (o instanceof Date) {
            setStatementParameter(statement, i, (Date) o);
        }
    }

//    abstract String createQueryString(Table table);

    private void setStatementParameter(PreparedStatement stmt, int parameterIndex, int value)
            throws SQLException {
        stmt.setInt(parameterIndex, value);
    }

    private void setStatementParameter(PreparedStatement stmt, int parameterIndex, String value)
            throws SQLException {
        stmt.setString(parameterIndex, value);
    }

    private void setStatementParameter(PreparedStatement stmt, int parameterIndex, short value)
            throws SQLException {
        stmt.setShort(parameterIndex, value);
    }

    private void setStatementParameter(PreparedStatement stmt, int parameterIndex, BigDecimal value)
            throws SQLException {
        stmt.setBigDecimal(parameterIndex, value);
    }

    private void setStatementParameter(PreparedStatement stmt, int parameterIndex, byte value)
            throws SQLException {
        stmt.setByte(parameterIndex, value);
    }

    private void setStatementParameter(PreparedStatement stmt, int parameterIndex, boolean value)
            throws SQLException {
        stmt.setBoolean(parameterIndex, value);
    }

    private void setStatementParameter(PreparedStatement stmt, int parameterIndex, long value)
            throws SQLException {
        stmt.setLong(parameterIndex, value);
    }

    private void setStatementParameter(PreparedStatement stmt, int parameterIndex, float value)
            throws SQLException {
        stmt.setFloat(parameterIndex, value);
    }

    private void setStatementParameter(PreparedStatement stmt, int parameterIndex, double value)
            throws SQLException {
        stmt.setDouble(parameterIndex, value);
    }

    private void setStatementParameter(PreparedStatement stmt, int parameterIndex, Date value)
            throws SQLException {
        stmt.setDate(parameterIndex, value);
    }
}

