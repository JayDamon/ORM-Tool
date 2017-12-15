package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;

import java.util.*;

public class SelectQuery extends Query {
    public SelectQuery(String tableName, String... columnNames) {
        super(tableName, columnNames);
    }

    public SelectQuery(String tableName, WhereClause whereClause, String... columnNames) {
        super(tableName, whereClause, columnNames);
    }

    @Override
    protected StringBuilder writeQuery(String tableName, ArrayList<Condition> conditions) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (conditions.size() <= 0) {
            sql.append("* ");
        } else {
            for (int i = 0; i < conditions.size(); i++) {
                if (i != 0) sql.append(", ");
                sql.append(conditions.get(i).getColumnName());
            }
            sql.append(" ");
        }
        sql.append("FROM ").append(tableName);
        return sql;
    }
}
