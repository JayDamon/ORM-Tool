package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.OrderByClause;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;

public abstract class Query {

    private StringBuilder queryBuilder;
    private String query;
    private WhereClause whereClause;

    public Query(String tableName, String... columnNames) {
        this.query = writeQuery(tableName, columnNames).toString();
    }

    public Query(String tableName, WhereClause whereClause, String... columnNames) {
        this.queryBuilder = writeQuery(tableName, columnNames);
        this.whereClause = whereClause;
        queryBuilder.append(whereClause.toString());
        this.query = queryBuilder.toString();
    }

    protected abstract StringBuilder writeQuery(String tableName, String... columnNames);

    public String toString() {
        return this.query;
    }

    public WhereClause getWhereClause() {
        return this.whereClause;
    }
}
