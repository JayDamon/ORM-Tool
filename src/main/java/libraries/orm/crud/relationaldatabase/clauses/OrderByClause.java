package libraries.orm.crud.relationaldatabase.clauses;

import libraries.orm.crud.Condition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderByClause extends Clause {

    public OrderByClause(ArrayList<Condition> conditionColumns) {
        super(conditionColumns);
    }

    @Override
    protected String writeClause(ArrayList<Condition> conditions) {
        StringBuilder sql = new StringBuilder(" ORDER BY ");
        for (int i = 0; i < conditions.size(); i++) {
            if (i != 0) sql.append(", ");
            sql.append(conditions.get(i).getColumnName());
        }
//        int i = 0;
//        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
//            if (i != 0) sql.append(", ");
//            sql.append(entry.getKey());
//            i++;
//        }
        return sql.toString();
    }
}
