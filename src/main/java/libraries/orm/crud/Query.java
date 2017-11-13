package libraries.orm.crud;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import libraries.orm.orm.Table;

public abstract class Query {
    public Query() {
    }

    public PreparedStatement createQuery(Connection conn, Table table) {
        return PrepareStatement.preparedStatement(conn, this.createQueryString(table));
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

