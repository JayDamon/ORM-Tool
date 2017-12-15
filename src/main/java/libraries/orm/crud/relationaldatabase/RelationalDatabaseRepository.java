//package libraries.orm.crud.relationaldatabase;
//
//import libraries.orm.crud.Condition;
//import libraries.orm.crud.CrudRepository;
//import libraries.orm.crud.Result;
//import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
//import libraries.orm.crud.relationaldatabase.preparedstatement.ORMPreparedStatement;
//import libraries.orm.crud.relationaldatabase.query.Query;
//import libraries.orm.orm.Crudable;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class RelationalDatabaseRepository<C extends Crudable> extends CrudRepository<Connection, Result<C>> {
//
//    public RelationalDatabaseRepository(Connection dataSource) {
//        super(dataSource);
//    }
//
//    @Override
//    public List<Result<C>> findAll() {
//        return null;
//    }
//
//    @Override
//    public List<Result<C>> findAll(String... columnNames) {
//        return null;
//    }
//
//    @Override
//    public List<Result<C>> findAll(List list, String... columnNames) {
//        return null;
//    }
//
//    @Override
//    public List<Result<C>> findAll(List list) {
//        return null;
//    }
//
//    private List<C> executeSelectQuery(Query query, Connection connection) {
//        List<C> list = new ArrayList<>();
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        ArrayList<SQLException> exceptions = new ArrayList<>();
//        ArrayList<Condition> conditions = new ArrayList<>();
//        WhereClause clause = query.getWhereClause();
//        if (clause != null && clause.getConditions() != null) {
//            conditions.addAll(clause.getConditions());
//        }
//        try {
//            statement = connection.prepareStatement(query.toString());
//            if (conditions.size() > 0) ORMPreparedStatement.setParameters(conditions, statement);
//            resultSet = statement.executeQuery();
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int columnCount = metaData.getColumnCount();
//            while (resultSet.next()) {
//                for (int column = 1; column <= columnCount; column++) {
//                    list.add(
//                            new Result<C>(
//                                    metaData.getColumnName(column),
//                                    resultSet.getObject(column))
//                    );
//                }
//            }
//        } catch (SQLException e) {
//            exceptions.add(e);
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//            } catch (SQLException e) {
//                exceptions.add(e);
//            }
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                exceptions.add(e);
//            }
//            if (exceptions.size() != 0) {
//                //ToDo log exceptions
//            }
//        }
//        return list;
//    }
//}
