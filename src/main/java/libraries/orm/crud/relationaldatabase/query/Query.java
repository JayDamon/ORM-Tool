package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class Query {

    private StringBuilder queryBuilder;
    private String query;
    private WhereClause whereClause;
    private ArrayList<Condition> columnNameAndValueList;

    public Query(String tableName, String... columnNames) {
        populateConditionsAndValues(columnNames);
        this.query = writeQuery(tableName, columnNameAndValueList).toString();
    }

    public Query(String tableName, ArrayList<Condition> columnNameAndValueList) {
        this.columnNameAndValueList = columnNameAndValueList;
        this.query = writeQuery(tableName, columnNameAndValueList).toString();
    }

    public Query(String tableName, WhereClause whereClause, String... columnNames) {
        populateConditionsAndValues(columnNames);
        setQueryDetails(tableName, whereClause, columnNameAndValueList);
    }

    public Query(String tableName, WhereClause whereClause, ArrayList<Condition> columnNameAndValueList) {
        this.columnNameAndValueList = columnNameAndValueList;
        setQueryDetails(tableName, whereClause, columnNameAndValueList);
    }

    private void setQueryDetails(String tableName, WhereClause whereClause, ArrayList<Condition> columnNameAndValueList) {
        this.queryBuilder = writeQuery(tableName, columnNameAndValueList);
        this.whereClause = whereClause;
        queryBuilder.append(whereClause.toString());
        this.query = queryBuilder.toString();
    }

    private void populateConditionsAndValues(String[] columnNames) {
        columnNameAndValueList = new ArrayList<>();
        for (String s : columnNames) {
            columnNameAndValueList.add(new Condition(s, null));
        }
    }

    protected abstract StringBuilder writeQuery(String tableName, ArrayList<Condition> conditionsAndValues);

    public String toString() {
        return this.query;
    }

    public WhereClause getWhereClause() {
        return this.whereClause;
    }

    public ArrayList<Condition> getColumnNameAndValueList() {
        return columnNameAndValueList;
    }
}
