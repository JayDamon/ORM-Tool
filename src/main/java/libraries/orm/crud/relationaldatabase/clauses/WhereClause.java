package libraries.orm.crud.relationaldatabase.clauses;

import libraries.orm.crud.Condition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WhereClause extends Clause {

    public WhereClause(ArrayList<Condition> conditions) {
        super(conditions);
    }

    protected String writeClause(ArrayList<Condition> conditions) {
        StringBuilder sql = new StringBuilder(" WHERE");
//        int i = 0;
//        for (Map.Entry<String, Object> entry : conditions.entrySet()) {
//            if (i != 0) sql.append(" AND");
//            sql.append(" ").append(entry.getKey()).append(" = ?");
//            i++;
//        }
        for (int i = 0 ; i < conditions.size() ; i++) {
            if (i != 0) sql.append(" AND");
            sql.append(" ").append(conditions.get(i).getColumnName()).append(" = ?");
        }
        return sql.toString();
    }
}
