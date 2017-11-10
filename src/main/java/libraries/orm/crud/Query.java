package libraries.orm.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;

import libraries.orm.orm.Table;

public abstract class Query {
    public Query() {
    }

    public PreparedStatement createQuery(Connection conn, Table table) {
        return PrepareStatement.preparedStatement(conn, this.createQueryString(table));
    }

    abstract String createQueryString(Table table);

}

