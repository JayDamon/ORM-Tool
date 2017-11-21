package libraries.orm.crud.relationaldatabase.preparedstatement;

import libraries.orm.orm.Table;

public class DeleteStatement extends ORMPreparedStatement {
    public DeleteStatement() {
    }

    String createQueryString(Table table) {
        return "DELETE FROM " + table.getTableName().name() + " WHERE " + table.getId().idColumnName() + " = ?";
    }
}
