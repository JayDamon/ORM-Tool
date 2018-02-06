package libraries.orm.crud.relationaldatabase;

import com.sun.org.apache.xpath.internal.operations.Bool;
import libraries.orm.annotations.ID;
import libraries.orm.crud.Condition;
import libraries.orm.crud.Crud;
import libraries.orm.crud.relationaldatabase.clauses.Clause;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.crud.relationaldatabase.preparedstatement.ORMPreparedStatement;
import libraries.orm.crud.relationaldatabase.query.*;
import libraries.orm.orm.Column;
import libraries.orm.orm.Crudable;
import libraries.orm.orm.Table;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RelationalDatabaseCrud<C extends Crudable, I> extends Crud<C, I> {

    public Connection connection;

    public RelationalDatabaseCrud(Class<C> crudable, Connection connection) {
        super(crudable);
        this.connection = connection;
    }

    @Override
    public boolean create(C crudable) {
        Query query = new InsertQuery(Table.getTableName(crudable.getClass()).name(), Table.getColumnAndValueList(crudable));
        ArrayList<Condition> conditions = getConditionsFromMap(query);
        return executeInsertQuery(query, conditions, crudable);
    }

    @Override
    public C findByID(I id) {
        Column col = Table.getIDColumn(crudable);
        Query query = new SelectQuery(
                Table.getTableName(crudable).name(),
                new WhereClause(
                        new ArrayList<>(Collections.singletonList(
                                new Condition(col.getColumn().name(), id)
                        ))
                )
        );
        List<C> result = executeSelectQuery(query);
        if (result.size() > 1) {
            throw new IllegalArgumentException("Query Returned multiple entries.");
        } else if (result.size() <= 0) {
            return null;
        }
        return result.get(0);
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
            statement = connection.prepareStatement(query.toString());
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
    public boolean update(C crudable) {
        Query query = new UpdateQuery(
                Table.getTableName(crudable.getClass()).name(),
                new WhereClause(
                        new ArrayList<>(
                                    Collections.singletonList(
                                        Table.getIDColumnAndValue(crudable)
                                ))),
                Table.getColumnAndValueList(crudable)
        );
        ArrayList<Condition> conditionsAndValueList = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditionsAndValueList);
    }

    @Override
    public boolean update(C crudable, ArrayList<Condition> conditions) {
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
    public boolean delete(C crudable) {
        Query query = new DeleteQuery(
                Table.getTableName(crudable.getClass()).name(),
                new WhereClause(
                        new ArrayList<>(
                                Collections.singletonList(
                                    Table.getIDColumnAndValue(crudable)
                                )))
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
    public boolean exists(C crudable) {
        Query query = new SelectQuery(
                Table.getTableName(crudable.getClass()).name(),
                new WhereClause(
                        new ArrayList<>(
                                Collections.singletonList(
                                    Table.getIDColumnAndValue(crudable)
                                ))),
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
            statement = connection.prepareStatement(query.toString());
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

    private boolean executeInsertQuery(Query query, ArrayList<Condition> conditions, C crudable) {
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            ORMPreparedStatement.setParameters(conditions, statement);
            int affectedRows = statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            boolean idSet = false;
            if (generatedKeys.next()) {
                Field[] fields = crudable.getClass().getDeclaredFields();
                for (Field f : fields) {
                    if (f.isAnnotationPresent(ID.class)) {
                        f.setAccessible(true);
                        setField(f, generatedKeys, crudable);
                        idSet = true;
                    }
                }
            }
            return affectedRows != 0 && idSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setField(Field field, ResultSet rs, Crudable c) throws IllegalAccessException, SQLException {
        Class<?> type = field.getType();
        //ToDo break out primative and object
        if (type.isPrimitive()) {
            if (type == boolean.class) {
                field.setBoolean(c, rs.getBoolean(1));
            }
            else if (type == byte.class) {
                field.setByte(c, rs.getByte(1));
            }
            else if (type == short.class) {
                field.setShort(c, rs.getShort(1));
            }
            else if (type == long.class) {
                field.setLong(c, rs.getLong(1));
            }
            else if (type == float.class) {
                field.setFloat(c, rs.getFloat(1));
            }
            else if (type == int.class) {
                field.setInt(c, rs.getInt(1));
            }
            else if (type == double.class) {
                field.setDouble(c, rs.getDouble(1));
            }
        } else {
            if (type == Boolean.class) {
                field.set(c, rs.getBoolean(1));
            }
            else if (type == Byte.class) {
                field.set(c, rs.getByte(1));
            }
            else if (type == Short.class) {
                field.set(c, rs.getShort(1));
            }
            else if (type == Long.class) {
                field.set(c, rs.getLong(1));
            }
            else if (type == Float.class) {
                field.set(c, rs.getFloat(1));
            }
            else if (type == Integer.class) {
                field.set(c, rs.getInt(1));
            }
            else if (type == Double.class) {
                field.set(c, rs.getDouble(1));
            }
            else if (type == String.class) {
                field.set(c, rs.getString(1));
            }
            else if (type == BigDecimal.class) {
                field.set(c, rs.getBigDecimal(1));
            }
            else if (type == BigInteger.class) {
                field.set(c, rs.getInt(1));
            }
        }

    }

    private boolean executeUpdateQuery(Query query, ArrayList<Condition> conditions) {
        try (
                PreparedStatement statement = connection.prepareStatement(query.toString())
        ) {
            ORMPreparedStatement.setParameters(conditions, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
