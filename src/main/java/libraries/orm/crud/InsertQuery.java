package libraries.orm.crud;

import java.util.List;
import libraries.orm.orm.Column;
import libraries.orm.orm.Table;

public class InsertQuery extends Query {
    public InsertQuery() {
    }

    String createQueryString(Table table) {
        StringBuilder columnNames = new StringBuilder("INSERT INTO ");
        columnNames.append(table.getTableName().name()).append(" (");
        List<Column> columns = table.getColumnList();
        StringBuilder values = new StringBuilder("VALUES(");

        for(int i = 0; i < columns.size(); ++i) {
            if (i != 0) {
                columnNames.append(", ");
                values.append(", ");
            }

            columnNames.append(columns.get(i).getColumnName().name());
            values.append("?");
        }

        columnNames.append(") ");
        values.append(")");
        columnNames.append(values);
        return columnNames.toString();
    }
}

