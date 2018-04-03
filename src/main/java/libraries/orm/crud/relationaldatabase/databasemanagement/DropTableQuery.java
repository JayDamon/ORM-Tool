package libraries.orm.crud.relationaldatabase.databasemanagement;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.query.Query;

import java.util.ArrayList;

public class DropTableQuery extends Query {

    public DropTableQuery(String tableName, String... columnNames) {
        super(tableName, columnNames);
    }

    @Override
    protected StringBuilder writeQuery(String tableName, ArrayList<Condition> conditionsAndValues) {
        return new StringBuilder("DROP TABLE IF EXISTS ").append(tableName).append(";");
    }
}
