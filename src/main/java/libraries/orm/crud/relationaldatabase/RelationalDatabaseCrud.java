package libraries.orm.crud.relationaldatabase;

import libraries.orm.crud.Crud;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.crud.relationaldatabase.preparedstatement.ORMPreparedStatement;
import libraries.orm.crud.relationaldatabase.query.InsertQuery;
import libraries.orm.crud.relationaldatabase.query.UpdateQuery;
import libraries.orm.orm.Crudable;
import libraries.orm.orm.Table;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelationalDatabaseCrud extends Crud<Connection> {

    private Table table;

    public RelationalDatabaseCrud(Crudable crudable, Connection database) {
        super(crudable, database);
        table = new Table(crudable);
    }

    @Override
    public boolean create() throws InvocationTargetException, IllegalAccessException {
        String[] columnNames = createColumnNameList(table);
        try (
                PreparedStatement statement = dataSource.prepareStatement(
                        new InsertQuery(table.getTableName().name(), columnNames).toString())
                ) {
            new ORMPreparedStatement().setParameters(table, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.iterator();
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
        String[] columnNames = createColumnNameList(table);
        try (
                PreparedStatement statement = dataSource.prepareStatement(
                        new UpdateQuery(
                                table.getTableName().name(),
                                new WhereClause(
                                        new String[]{table.getId().idColumnName()}
                                        ),
                                columnNames
                    ).toString()
                )
                ) {
            new ORMPreparedStatement().setParameters(table, statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
//        try (
//                PreparedStatement statement = ORMPreparedStatement.createQuery(dataSource, new Table(crudable))
//                ) {
//            return statement.executeUpdate() == 1;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    private static String[] createColumnNameList(Table table) {
        String[] columnNames = new String[table.getColumnNameList().size()];
        return table.getColumnNameList().toArray(columnNames);
    }

}
