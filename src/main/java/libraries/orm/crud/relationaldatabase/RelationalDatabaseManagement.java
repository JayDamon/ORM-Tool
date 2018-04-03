package libraries.orm.crud.relationaldatabase;

import libraries.orm.crud.DatabaseManagement;
import libraries.orm.crud.relationaldatabase.databasemanagement.DropTableQuery;
import libraries.orm.crud.relationaldatabase.query.Query;
import libraries.orm.orm.Crudable;
import libraries.orm.orm.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RelationalDatabaseManagement<C extends Crudable> extends DatabaseManagement<C> {

    public Connection connection;

    public RelationalDatabaseManagement(Class<C> crudable, Connection connection) {
        super(crudable);
        this.connection = connection;
    }

    @Override
    public boolean dropTable(C crudable) {
        Query query = new DropTableQuery(Table.getTableName(crudable.getClass()).name());
        return executeDropTableQuery(query);
    }

    private boolean executeDropTableQuery(Query query) {
        try (
                PreparedStatement statement = connection.prepareStatement(query.toString());
        ) {
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean tableCreated(C crudable) {

        return false;
    }
}
