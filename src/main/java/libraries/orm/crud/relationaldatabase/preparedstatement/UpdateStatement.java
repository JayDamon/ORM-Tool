package libraries.orm.crud.relationaldatabase.preparedstatement;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import libraries.orm.orm.Column;
import libraries.orm.orm.Table;

public class UpdateStatement extends ORMPreparedStatement {
    public UpdateStatement() {
    }

    String createQueryString(Table table) {
        List<Column> columns = table.getColumnList();
        StringBuilder updateQuery = new StringBuilder("UPDATE ");
        updateQuery.append(table.getTableName().name()).append(" SET ");

        for(int i = 0; i < columns.size(); ++i) {
            updateQuery.append(columns.get(i).getColumnName().name()).append(" = ?");
            if (i != columns.size() - 1) {
                updateQuery.append(",");
            }
            updateQuery.append(" ");
        }
        updateQuery.append("WHERE ").append(table.getId().idColumnName()).append(" = ?");
        return updateQuery.toString();
    }

}