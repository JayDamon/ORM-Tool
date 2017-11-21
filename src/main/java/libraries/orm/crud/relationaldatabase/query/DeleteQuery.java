package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.WhereClause;

public class DeleteQuery extends Query {

    public DeleteQuery(String tableName, WhereClause whereClause) {
        super(tableName, whereClause);
    }

    @Override
    protected StringBuilder writeQuery(String tableName, String... columnNames) {
        return new StringBuilder("DELETE FROM " + tableName);
    }
}
