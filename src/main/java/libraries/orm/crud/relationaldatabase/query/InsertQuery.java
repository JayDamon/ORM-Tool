package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Column;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InsertQuery extends Query {

    public InsertQuery(String tableName, LinkedHashMap<String, Object> conditionsAndValues) {
        super(tableName, conditionsAndValues);
    }

    public InsertQuery(String tableName, WhereClause whereClause, LinkedHashMap<String, Object> conditionsAndValues) {
        super(tableName, whereClause, conditionsAndValues);
    }

    @Override
    public StringBuilder writeQuery(String tableName, LinkedHashMap<String, Object> conditionsAndValues) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (");
        StringBuilder values = new StringBuilder("VALUES(");

        int i = 0;
        for (Map.Entry<String, Object> entry : conditionsAndValues.entrySet()) {
            if (i != 0) {
                sql.append(", ");
                values.append(", ");
            }
            sql.append(entry.getKey());
            values.append("?");
            i++;
        }

        sql.append(") ");
        values.append(")");
        sql.append(values);
        return sql;
    }
}
