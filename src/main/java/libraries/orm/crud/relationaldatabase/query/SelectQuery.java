package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.WhereClause;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SelectQuery extends Query {
    public SelectQuery(String tableName, String... columnNames) {
        super(tableName, columnNames);
    }

    public SelectQuery(String tableName, WhereClause whereClause, String... columnNames) {
        super(tableName, whereClause, columnNames);
    }

    @Override
    protected StringBuilder writeQuery(String tableName, LinkedHashMap<String, Object> conditionsAndValues) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (conditionsAndValues.size() <= 0) {
            sql.append("* ");
        } else {
            int i = 0;
            for (Map.Entry<String, Object> entry : conditionsAndValues.entrySet()) {
                if (i != 0) sql.append(", ");
                sql.append(entry.getKey());
                i++;
            }
            sql.append(" ");
        }
        sql.append("FROM ").append(tableName);
        return sql;
    }
}
