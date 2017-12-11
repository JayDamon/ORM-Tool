package libraries.orm.crud.relationaldatabase.clauses;

import java.util.LinkedHashMap;
import java.util.Map;

public class WhereClause extends Clause {

    public WhereClause(LinkedHashMap<String, Object> conditions) {
        super(conditions);
    }

    protected String writeClause(LinkedHashMap<String, Object> conditions) {
        StringBuilder sql = new StringBuilder(" WHERE");
        int i = 0;
        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
            if (i != 0) sql.append(" AND");
            sql.append(" ").append(entry.getKey()).append(" = ?");
            i++;
        }
        return sql.toString();
    }
}
