package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.WhereClause;

public abstract class Query {

    private StringBuilder queryBuilder;
    private String query;

    public Query(String tableName, String... columnNames) {
        this.query = writeQuery(tableName, columnNames).toString();
    }

    public Query(String tableName, WhereClause whereClause, String... columnNames) {
        this.queryBuilder = writeQuery(tableName, columnNames);
        queryBuilder.append(whereClause.toString());
        this.query = queryBuilder.toString();
    }

    protected abstract StringBuilder writeQuery(String tableName, String... columnNames);

    public String toString() {
        return this.query;
    }
}
