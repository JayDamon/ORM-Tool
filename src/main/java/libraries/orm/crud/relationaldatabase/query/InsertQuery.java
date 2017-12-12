package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Column;

import java.util.*;

public class InsertQuery extends Query {

    public InsertQuery(String tableName, ArrayList<Condition> conditionsAndValues) {
        super(tableName, conditionsAndValues);
    }

    public InsertQuery(String tableName, WhereClause whereClause, ArrayList<Condition> conditionsAndValues) {
        super(tableName, whereClause, conditionsAndValues);
    }

    @Override
    public StringBuilder writeQuery(String tableName, ArrayList<Condition> conditionsAndValues) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(tableName).append(" (");
        StringBuilder values = new StringBuilder("VALUES(");
        for (int i = 0; i < conditionsAndValues.size(); i++) {
            if (i != 0) {
                sql.append(", ");
                values.append(", ");
            }
            sql.append(conditionsAndValues.get(i).getColumnName());
            values.append("?");
        }
//        int i = 0;
//        for (Map.Entry<String, Object> entry : conditionsAndValues.entrySet()) {
//            if (i != 0) {
//                sql.append(", ");
//                values.append(", ");
//            }
//            sql.append(entry.getKey());
//            values.append("?");
//            i++;
//        }

        sql.append(") ");
        values.append(")");
        sql.append(values);
        return sql;
    }
}
