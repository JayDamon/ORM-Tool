package libraries.orm.crud;

import libraries.orm.orm.Table;

public class DeletePreparedStatement extends PreparedStatement {
    public DeletePreparedStatement() {
    }

    String createQueryString(Table table) {
        return "DELETE FROM " + table.getTableName().name() + " WHERE " + table.getId().idColumnName() + " = ?";
    }
}
