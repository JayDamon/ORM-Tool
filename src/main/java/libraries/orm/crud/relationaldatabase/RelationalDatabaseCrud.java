package libraries.orm.crud.relationaldatabase;

import libraries.orm.crud.Crud;
import libraries.orm.crud.relationaldatabase.preparedstatement.InsertStatement;
import libraries.orm.crud.relationaldatabase.preparedstatement.ORMPreparedStatement;
import libraries.orm.crud.relationaldatabase.preparedstatement.UpdateStatement;
import libraries.orm.orm.Crudable;
import libraries.orm.orm.Table;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationalDatabaseCrud extends Crud<Connection> {

    public RelationalDatabaseCrud(Crudable crudable, Connection database) {
        super(crudable, database);
    }

    @Override
    public boolean create() throws InvocationTargetException, IllegalAccessException {
        ORMPreparedStatement ORMPreparedStatement = new InsertStatement();
        try (
                java.sql.PreparedStatement statement = ORMPreparedStatement.createQuery(dataSource, new Table(crudable))
                ) {
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<?> read() { //ToDo change type
        List<?> list = new ArrayList<>();
        return list;
    }

    @Override
    public boolean update() throws InvocationTargetException, IllegalAccessException {
        ORMPreparedStatement ORMPreparedStatement = new UpdateStatement();
        try (
                java.sql.PreparedStatement statement = ORMPreparedStatement.createQuery(dataSource, new Table(crudable))
                ) {
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete() {
        return false;
    }

}
