package libraries.orm.crud.relationaldatabase;

import libraries.orm.crud.Crud;
import libraries.orm.crud.relationaldatabase.clauses.Clause;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
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
        LinkedHashMap<String, Object> conditionsAndValueList = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditionsAndValueList);
    }

    @Override
    public List<Map<String, Object>> read() {
        List<Map<String, Object>> list = new ArrayList<>();
        Query query = new SelectQuery(
                table.getTableName().name()
        );
        try (
                PreparedStatement statement = dataSource.prepareStatement(query.toString());
                ResultSet rs = statement.executeQuery();
                ) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                Map<String, Object> columnList = new HashMap<>();
                list.add(columnList);
                for (int column = 1; column <= columnCount; column++) {
                    columnList.put(
                            metaData.getColumnName(column),
                            rs.getObject(column)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        LinkedHashMap<String, Object> conditionsAndValueList = getConditionsFromMap(query);
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
        LinkedHashMap<String, Object> conditions = getConditionsFromMap(query);
        return executeUpdateQuery(query, conditions);
    }

    private LinkedHashMap<String, Object> getConditionsFromMap(Query query) {
        LinkedHashMap<String, Object> conditionsAndValueList = new LinkedHashMap<>(query.getColumnNameAndValueList());
        Clause whereClause = query.getWhereClause();
        if (whereClause != null && whereClause.getConditions() != null) {
            conditionsAndValueList.putAll(whereClause.getConditions());
        }
        return conditionsAndValueList;
    }

    private boolean executeUpdateQuery(Query query, LinkedHashMap<String, Object> conditionsAndValueList) {
        try (
                PreparedStatement statement = dataSource.prepareStatement(query.toString())
        ) {
            ORMPreparedStatement.setParameters(conditionsAndValueList, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
