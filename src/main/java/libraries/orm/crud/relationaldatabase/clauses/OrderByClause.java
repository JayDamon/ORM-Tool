package libraries.orm.crud.relationaldatabase.clauses;

public class OrderByClause extends Clause {

    public OrderByClause(String[] conditionColumns) {
        super(conditionColumns);
    }

    @Override
    protected String writeClause(String... conditionColumns) {
        StringBuilder sql = new StringBuilder(" ORDER BY ");
        for (int i = 0; i < conditionColumns.length; i++) {
            if (i != 0) sql.append(", ");
            sql.append(conditionColumns[i]);
        }
        return sql.toString();
    }
}
