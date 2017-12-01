package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.WhereClause;

import java.util.LinkedHashMap;

public abstract class Query {

    private StringBuilder queryBuilder;
    private String query;
    private WhereClause whereClause;
    private LinkedHashMap<String, Object> columnNameAndValueList;

    public Query(String tableName, String... columnNames) {
        populateConditionsAndValues(columnNames);
        this.query = writeQuery(tableName, columnNameAndValueList).toString();
    }

    public Query(String tableName, LinkedHashMap<String, Object> columnNameAndValueList) {
        this.columnNameAndValueList = columnNameAndValueList;
        this.query = writeQuery(tableName, columnNameAndValueList).toString();
    }

    public Query(String tableName, WhereClause whereClause, String... columnNames) {
        populateConditionsAndValues(columnNames);
        setQueryDetails(tableName, whereClause, columnNameAndValueList);
    }

    public Query(String tableName, WhereClause whereClause, LinkedHashMap<String, Object> columnNameAndValueList) {
        this.columnNameAndValueList = columnNameAndValueList;
        setQueryDetails(tableName, whereClause, columnNameAndValueList);
    }

    private void setQueryDetails(String tableName, WhereClause whereClause, LinkedHashMap<String, Object> columnNameAndValueList) {
        this.queryBuilder = writeQuery(tableName, columnNameAndValueList);
        this.whereClause = whereClause;
        queryBuilder.append(whereClause.toString());
        this.query = queryBuilder.toString();
    }

    private void populateConditionsAndValues(String[] columnNames) {
        columnNameAndValueList = new LinkedHashMap<>();
        for (String s : columnNames) {
            columnNameAndValueList.put(s, null);
        }
    }

    protected abstract StringBuilder writeQuery(String tableName, LinkedHashMap<String, Object> conditionsAndValues);

    public String toString() {
        return this.query;
    }

    public WhereClause getWhereClause() {
        return this.whereClause;
    }

    public LinkedHashMap<String, Object> getColumnNameAndValueList() {
        return columnNameAndValueList;
    }
}
