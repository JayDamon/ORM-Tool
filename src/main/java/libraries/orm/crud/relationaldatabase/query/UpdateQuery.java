package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Column;

import java.util.List;

public class UpdateQuery extends Query {

    public UpdateQuery(String tableName, WhereClause whereClause, String... columnNames) {
        super(tableName, whereClause, columnNames);
    }

    @Override
    public StringBuilder writeQuery(String tableName, String... columnNames) {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(tableName).append(" SET");

        for(int i = 0; i < columnNames.length; ++i) {
            sql.append(" ").append(columnNames[i]).append(" = ?");
            if (i != columnNames.length - 1) {
                sql.append(",");
            }
        }
        return sql;
    }
}
