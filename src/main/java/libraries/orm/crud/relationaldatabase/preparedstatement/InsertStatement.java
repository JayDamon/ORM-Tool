package libraries.orm.crud.relationaldatabase.preparedstatement;

import java.util.List;
import libraries.orm.orm.Column;
import libraries.orm.orm.Table;

public class InsertStatement extends ORMPreparedStatement {

    public InsertStatement() {
    }

    String createQueryString(Table table) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(table.getTableName().name()).append(" (");
        List<Column> columns = table.getColumnList();
        StringBuilder values = new StringBuilder("VALUES(");

        for(int i = 0; i < columns.size(); ++i) {
            if (i != 0) {
                sql.append(", ");
                values.append(", ");
            }

            sql.append(columns.get(i).getColumnName().name());
            values.append("?");
        }

        sql.append(") ");
        values.append(")");
        sql.append(values);
        return sql.toString();
    }

}

