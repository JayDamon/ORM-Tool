package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Column;

import java.util.List;

public class InsertQuery extends Query {

    public InsertQuery(String tableName, String... columnNames) {
        super(tableName, columnNames);
    }

    public InsertQuery(String tableName, WhereClause whereClause, String... columnNames) {
        super(tableName, whereClause, columnNames);
    }

    @Override
    public StringBuilder writeQuery(String tableName, String... columnNames) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (");
        StringBuilder values = new StringBuilder("VALUES(");

        for(int i = 0; i < columnNames.length; ++i) {
            if (i != 0) {
                sql.append(", ");
                values.append(", ");
            }
            sql.append(columnNames[i]);
            values.append("?");
        }

        sql.append(") ");
        values.append(")");
        sql.append(values);
        return sql;
    }
}
