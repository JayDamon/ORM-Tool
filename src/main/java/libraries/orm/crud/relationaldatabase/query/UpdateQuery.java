package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Column;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UpdateQuery extends Query {

    public UpdateQuery(String tableName, WhereClause whereClause, LinkedHashMap<String, Object> conditionsAndValues) {
        super(tableName, whereClause, conditionsAndValues);
    }

    @Override
    public StringBuilder writeQuery(String tableName, LinkedHashMap<String, Object> conditionsAndValues) {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tableName).append(" SET");

        int i = 0;
        for (Map.Entry<String, Object> entry : conditionsAndValues.entrySet()) {
            sql.append(" ").append(entry.getKey()).append(" = ?");
            if (i != conditionsAndValues.size() - 1) {
                sql.append(",");
            }
            i++;
        }
        return sql;
    }
}
