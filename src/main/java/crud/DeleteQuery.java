package crud;

import orm.Table;

public class DeleteQuery extends Query {
    public DeleteQuery() {
    }

    String createQueryString(Table table) {
        return "DELETE FROM " + table.getTableName().name() + " WHERE " + table.getId().idColumnName() + " = ?";
    }
}
