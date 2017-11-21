package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.WhereClause;

import java.util.List;

public class SelectQuery extends Query {
    public SelectQuery(String tableName, String... columnNames) {
        super(tableName, columnNames);
    }

    public SelectQuery(String tableName, WhereClause whereClause, String... columnNames) {
        super(tableName,whereClause, columnNames);
    }

    @Override
    protected StringBuilder writeQuery(String tableName, String... columnNames) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (columnNames.length <= 0) {
            sql.append("* ");
        } else {
            for (int i = 0 ; i < columnNames.length; i++) {
                if (i != 0) sql.append(", ");
                sql.append(columnNames[i]);
            }
            sql.append(" ");
        }
        sql.append("FROM ").append(tableName);
        return sql;
    }
}
