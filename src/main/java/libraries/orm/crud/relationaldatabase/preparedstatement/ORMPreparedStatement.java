package libraries.orm.crud.relationaldatabase.preparedstatement;

import libraries.orm.crud.Condition;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class ORMPreparedStatement {
    public ORMPreparedStatement() {
    }

    public static void setParameters(ArrayList<Condition> parameterList, PreparedStatement statement)
            throws SQLException {
        for (int i = 0; i < parameterList.size(); i++) {
            setParameterBasedOnType(statement, i + 1, parameterList.get(i).getCondition());
        }
    }

    private static void setParameterBasedOnType(PreparedStatement statement, int i, Object o)
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
        } else if (o instanceof Calendar) {
            setStatementParameter(statement, i, new Date(((Calendar) o).getTimeInMillis()));
        }
    }

    private static void setStatementParameter(PreparedStatement stmt, int parameterIndex, int value)
            throws SQLException {
        stmt.setInt(parameterIndex, value);
    }

    private static void setStatementParameter(PreparedStatement stmt, int parameterIndex, String value)
            throws SQLException {
        stmt.setString(parameterIndex, value);
    }

    private static void setStatementParameter(PreparedStatement stmt, int parameterIndex, short value)
            throws SQLException {
        stmt.setShort(parameterIndex, value);
    }

    private static void setStatementParameter(PreparedStatement stmt, int parameterIndex, BigDecimal value)
            throws SQLException {
        stmt.setBigDecimal(parameterIndex, value);
    }

    private static void setStatementParameter(PreparedStatement stmt, int parameterIndex, byte value)
            throws SQLException {
        stmt.setByte(parameterIndex, value);
    }

    private static void setStatementParameter(PreparedStatement stmt, int parameterIndex, boolean value)
            throws SQLException {
        stmt.setBoolean(parameterIndex, value);
    }

    private static void setStatementParameter(PreparedStatement stmt, int parameterIndex, long value)
            throws SQLException {
        stmt.setLong(parameterIndex, value);
    }

    private static void setStatementParameter(PreparedStatement stmt, int parameterIndex, float value)
            throws SQLException {
        stmt.setFloat(parameterIndex, value);
    }

    private static void setStatementParameter(PreparedStatement stmt, int parameterIndex, double value)
            throws SQLException {
        stmt.setDouble(parameterIndex, value);
    }

    private static void setStatementParameter(PreparedStatement stmt, int parameterIndex, Date value)
            throws SQLException {
        stmt.setDate(parameterIndex, value);
    }
}

