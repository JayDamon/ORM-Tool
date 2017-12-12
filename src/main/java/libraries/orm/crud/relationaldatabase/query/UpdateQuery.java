package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Column;

import java.util.*;

public class UpdateQuery extends Query {

    public UpdateQuery(String tableName, WhereClause whereClause, ArrayList<Condition> conditionsAndValues) {
        super(tableName, whereClause, conditionsAndValues);
    }

    @Override
    public StringBuilder writeQuery(String tableName, ArrayList<Condition> conditionsAndValues) {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tableName).append(" SET");

        for (int i = 0; i < conditionsAndValues.size(); i++) {
            sql.append(" ").append(conditionsAndValues.get(i).getColumnName()).append(" = ?");
            if (i != conditionsAndValues.size() - 1) {
                sql.append(",");
            }
        }

//        int i = 0;
//        for (Map.Entry<String, Object> entry : conditionsAndValues.entrySet()) {
//            sql.append(" ").append(entry.getKey()).append(" = ?");
//            if (i != conditionsAndValues.size() - 1) {
//                sql.append(",");
//            }
//            i++;
//        }
        return sql;
    }
}
