package libraries.orm.crud.relationaldatabase.clauses;

public class WhereClause extends Clause {

    public WhereClause(String[] conditionColumns) {
        super(conditionColumns);
    }

    protected String writeClause(String... conditionColumns) {
        StringBuilder sql = new StringBuilder(" WHERE");
        for (int i = 0; i < conditionColumns.length; i++) {
            if (i != 0) sql.append(" AND");
            sql.append(" ").append(conditionColumns[i]).append(" = ?");
        }
        return sql.toString();
    }
}
