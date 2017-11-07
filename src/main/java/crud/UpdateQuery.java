package crud;

import java.util.List;
import orm.Column;
import orm.Table;

public class UpdateQuery extends Query {
    public UpdateQuery() {
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
