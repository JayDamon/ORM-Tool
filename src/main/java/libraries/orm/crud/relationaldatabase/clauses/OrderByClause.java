package libraries.orm.crud.relationaldatabase.clauses;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderByClause extends Clause {

    public OrderByClause(LinkedHashMap<String, Object> conditionColumns) {
        super(conditionColumns);
    }

    @Override
    protected String writeClause(LinkedHashMap<String, Object> conditions) {
        StringBuilder sql = new StringBuilder(" ORDER BY ");
        int i = 0;
        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
            if (i != 0) sql.append(", ");
            sql.append(entry.getKey());
            i++;
        }
        return sql.toString();
    }
}
