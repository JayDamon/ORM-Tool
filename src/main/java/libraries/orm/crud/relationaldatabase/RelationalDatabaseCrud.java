package libraries.orm.crud.relationaldatabase;

import libraries.orm.crud.Condition;
import libraries.orm.crud.Crud;
import libraries.orm.crud.relationaldatabase.clauses.Clause;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.crud.relationaldatabase.exceptions.QueryException;
import libraries.orm.crud.relationaldatabase.preparedstatement.ORMPreparedStatement;
import libraries.orm.crud.relationaldatabase.query.*;
import libraries.orm.orm.Crudable;
import libraries.orm.orm.Table;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class RelationalDatabaseCrud extends Crud<Connection> {

    private Table table;

    public RelationalDatabaseCrud(Crudable crudable, Connection database) {
        super(crudable, database);
        table = new Table(crudable);
    }

    @Override
    public boolean create() throws InvocationTargetException, IllegalAccessException {
        Query query = new InsertQuery(table.getTableName().name(), table.getColumnAndValueList());
        ArrayList<Condition> conditions = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditions);
    }

    @Override
    public List<ArrayList<Condition>> read() {
        Query query = new SelectQuery(
                table.getTableName().name()
        );
        return executeSelectQuery(query, dataSource);
    }

    @Override
    public List<ArrayList<Condition>> read(ArrayList<Condition> conditions) {
        Query query = new SelectQuery(
                table.getTableName().name(),
                new WhereClause(conditions)
        );
        return executeSelectQuery(query, dataSource);
    }

    @Override
    public List<ArrayList<Condition>> read(ArrayList<Condition> conditions, String... columnNames) {
        Query query = new SelectQuery(
                table.getTableName().name(),
                new WhereClause(conditions),
                columnNames
        );
        return executeSelectQuery(query, dataSource);
    }

    private static List<ArrayList<Condition>> executeSelectQuery(Query query, Connection connection) {
        List<ArrayList<Condition>> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<SQLException> exceptions = new ArrayList<>();
        ArrayList<Condition> conditions = getConditionsFromMap(query);
        try {
            statement = connection.prepareStatement(query.toString());
            if (conditions != null) ORMPreparedStatement.setParameters(conditions, statement);
            resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                ArrayList<Condition> columnList = new ArrayList<>();
                list.add(columnList);
                for (int column = 1; column <= columnCount; column++) {
                    columnList.add(
                            new Condition(
                            metaData.getColumnName(column),
                            resultSet.getObject(column))
                    );
                }
            }
        } catch (SQLException e) {
            exceptions.add(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                exceptions.add(e);
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                exceptions.add(e);
            }
            if (exceptions.size() != 0) {
                //ToDo log exceptions
            }
        }
        return list;
    }

    @Override
    public boolean update() throws InvocationTargetException, IllegalAccessException {
        Query query = new UpdateQuery(
                table.getTableName().name(),
                new WhereClause(
                        table.getIDColumnAndValue()
                ),
                table.getColumnAndValueList()
        );
        ArrayList<Condition> conditionsAndValueList = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditionsAndValueList);
    }

    @Override
    public boolean update(ArrayList<Condition> conditions) throws InvocationTargetException, IllegalAccessException {
        Query query = new UpdateQuery(
                table.getTableName().name(),
                new WhereClause(
                        conditions
                ),
                table.getColumnAndValueList()
        );
        ArrayList<Condition> conditionsAndValueList = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditionsAndValueList);
    }

    @Override
    public boolean delete() throws InvocationTargetException, IllegalAccessException {
        Query query = new DeleteQuery(
                table.getTableName().name(),
                new WhereClause(
                        table.getIDColumnAndValue()
                )
        );
        ArrayList<Condition> conditions = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditions);
    }

    @Override
    public boolean exists(ArrayList<Condition> conditions) {
        Query query = new SelectQuery(
                table.getTableName().name(),
                new WhereClause(
                        conditions
                )
        );
        return exists(query);
    }

    @Override
    public boolean exists() throws InvocationTargetException, IllegalAccessException {
        Query query = new SelectQuery(
                table.getTableName().name(),
                new WhereClause(
                        table.getIDColumnAndValue()
                )
        );
        return exists(query);
    }

    private boolean exists(Query query) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<SQLException> exceptions = new ArrayList<>();
        ArrayList<Condition> conditions = getConditionsFromMap(query);
        boolean exists = false;
        try {
            statement = dataSource.prepareStatement(query.toString());
            ORMPreparedStatement.setParameters(conditions, statement);
            resultSet = statement.executeQuery();
            exists = resultSet.next();
        } catch (SQLException e) {
            exceptions.add(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                exceptions.add(e);
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                exceptions.add(e);
            }
            if (exceptions.size() != 0) {
                //ToDo log exceptions
            }
        }
        return exists;
    }

    private static ArrayList<Condition> getConditionsFromMap(Query query) {
        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.addAll(query.getColumnNameAndValueList());
        Clause whereClause = query.getWhereClause();
        if (whereClause != null && whereClause.getConditions() != null) {
            conditions.addAll(whereClause.getConditions());
        }
        return conditions;
    }

    private boolean executeUpdateQuery(Query query, ArrayList<Condition> conditions) {
        try (
                PreparedStatement statement = dataSource.prepareStatement(query.toString())
        ) {
            ORMPreparedStatement.setParameters(conditions, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
