package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DeleteQuery extends Query {

    public DeleteQuery(String tableName, WhereClause whereClause) {
        super(tableName, whereClause);
    }

    @Override
    protected StringBuilder writeQuery(String tableName, ArrayList<Condition> columnAndValueList) {
        return new StringBuilder("DELETE FROM " + tableName);
    }
}
