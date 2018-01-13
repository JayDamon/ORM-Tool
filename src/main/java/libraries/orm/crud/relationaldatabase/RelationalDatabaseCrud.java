package libraries.orm.crud.relationaldatabase;

import libraries.orm.crud.Condition;
import libraries.orm.crud.Crud;
import libraries.orm.crud.relationaldatabase.clauses.Clause;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.crud.relationaldatabase.preparedstatement.ORMPreparedStatement;
import libraries.orm.crud.relationaldatabase.query.*;
import libraries.orm.orm.Crudable;
import libraries.orm.orm.Table;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelationalDatabaseCrud<C extends Crudable> extends Crud<C, Connection> {

    public RelationalDatabaseCrud(Class<C> crudable, Connection connection) {
        super(crudable, connection);
    }

    @Override
    public boolean create(C crudable) throws InvocationTargetException, IllegalAccessException {
        Query query = new InsertQuery(Table.getTableName(crudable.getClass()).name(), Table.getColumnAndValueList(crudable));
        ArrayList<Condition> conditions = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditions);
    }

    @Override
    public List<C> read() {
        Query query = new SelectQuery(
                Table.getTableName(crudable).name()
        );
        return executeSelectQuery(query);
    }

    @Override
    public List<C> read(String... columnNames) {
        Query query = new SelectQuery(
                Table.getTableName(crudable).name(),
                columnNames
        );
        return executeSelectQuery(query);
    }

    @Override
    public List<C> read(ArrayList<Condition> conditions) {
        Query query = new SelectQuery(
                Table.getTableName(crudable).name(),
                new WhereClause(conditions)
        );
        return executeSelectQuery(query);
    }

    @Override
    public List<C> read(ArrayList<Condition> conditions, String... columnNames) {
        Query query = new SelectQuery(
                Table.getTableName(crudable).name(),
                new WhereClause(conditions),
                columnNames
        );
        return executeSelectQuery(query);
    }

    private List<C> executeSelectQuery(Query query) {
        List<C> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<SQLException> exceptions = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        WhereClause clause = query.getWhereClause();
        if (clause != null && clause.getConditions() != null) {
            conditions.addAll(clause.getConditions());
        }
        try {
            statement = dataSource.prepareStatement(query.toString());
            if (conditions.size() > 0) ORMPreparedStatement.setParameters(conditions, statement);
            resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> results = new HashMap<>();
                for (int column = 1; column <= columnCount; column++) {
                    results.put(
                            metaData.getColumnName(column).toUpperCase(),
                            resultSet.getObject(column)
                    );
                }
                list.add(CrudableFactory.getCrudable(crudable, results));
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
    public boolean update(C crudable) throws InvocationTargetException, IllegalAccessException {
        Query query = new UpdateQuery(
                Table.getTableName(crudable.getClass()).name(),
                new WhereClause(
                        Table.getIDColumnAndValue(crudable)
                ),
                Table.getColumnAndValueList(crudable)
        );
        ArrayList<Condition> conditionsAndValueList = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditionsAndValueList);
    }

    @Override
    public boolean update(C crudable, ArrayList<Condition> conditions) throws InvocationTargetException, IllegalAccessException {
        Query query = new UpdateQuery(
                Table.getTableName(crudable.getClass()).name(),
                new WhereClause(
                        conditions
                ),
                Table.getColumnAndValueList(crudable)
        );
        ArrayList<Condition> conditionsAndValueList = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditionsAndValueList);
    }

    @Override
    public boolean delete(C crudable) throws InvocationTargetException, IllegalAccessException {
        Query query = new DeleteQuery(
                Table.getTableName(crudable.getClass()).name(),
                new WhereClause(
                        Table.getIDColumnAndValue(crudable)
                )
        );
        ArrayList<Condition> conditions = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditions);
    }

    @Override
    public boolean exists(C crudable, ArrayList<Condition> conditions) {
        Query query = new SelectQuery(
                Table.getTableName(crudable.getClass()).name(),
                new WhereClause(
                        conditions
                ),
                "COUNT(1) As ItemExists"
        );
        return exists(query);
    }

    @Override
    public boolean exists(C crudable) throws InvocationTargetException, IllegalAccessException {
        Query query = new SelectQuery(
                Table.getTableName(crudable.getClass()).name(),
                new WhereClause(
                        Table.getIDColumnAndValue(crudable)
                ),
                "COUNT(1) As ItemExists"
        );
        return exists(query);
    }

    private boolean exists(Query query) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<SQLException> exceptions = new ArrayList<>();
        ArrayList<Condition> conditions = new ArrayList<>();
        WhereClause clause = query.getWhereClause();
        if (clause != null && clause.getConditions() != null) {
            conditions.addAll(clause.getConditions());
        }
        boolean exists = false;
        try {
            statement = dataSource.prepareStatement(query.toString());
            ORMPreparedStatement.setParameters(conditions, statement);
            resultSet = statement.executeQuery();
            resultSet.next();
            exists = resultSet.getInt("ItemExists") == 1;
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
        ArrayList<Condition> conditions = new ArrayList<>(query.getColumnNameAndValueList());
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
