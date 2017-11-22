package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.clauses.OrderByClause;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Table;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;

import java.sql.Date;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryTest {
    static private Table table;
    static private String[] columnNames;

    @BeforeAll
    static void setup() {
        POJOWithAnnotations pojo = new POJOWithAnnotations();
        pojo.setId(2);
        pojo.setTestString("TestValue");
        pojo.setTestInt(20);
        pojo.setTestDouble(30.0D);
        pojo.setTestDate(new Date(2017, 11, 5));

        table = new Table(pojo);
        columnNames = new String[table.getColumnNameList().size()];
        columnNames = table.getColumnNameList().toArray(columnNames);
    }

    @Test
    public void createInsertQuery() {
        assertEquals(
                "INSERT INTO testTableName (testString, testInt, testDouble, testDate) VALUES(?, ?, ?, ?)",
                new InsertQuery(
                        table.getTableName().name(),
                        columnNames
                ).toString()
        );
    }

    @Test
    public void createUpdateQueryWithWhere() {
        assertEquals(
                "UPDATE testTableName " +
                        "SET testString = ?, testInt = ?, testDouble = ?, testDate = ? WHERE id = ?",
                new UpdateQuery(
                        table.getTableName().name(),
                        new WhereClause(
                                new String[]{table.getId().idColumnName()}
                                ),
                        columnNames
                ).toString()
        );
    }

    @Test
    public void createVanillaSelectQuery() {
        assertEquals(
                "SELECT * FROM testTableName",
                new SelectQuery(
                        table.getTableName().name()
                ).toString()
        );
    }

    @Test
    public void createSelectQueryWithOneColumn() {
        assertEquals(
                "SELECT testString FROM testTableName",
                new SelectQuery(
                        table.getTableName().name(),
                        table.getColumnNameList().get(0)
                ).toString()
        );
    }

    @Test
    public void createSelectQueryWithMultipleColumns() {
        String[] args = new String[table.getColumnNameList().size()];
        args = table.getColumnNameList().toArray(args);
        assertEquals(
                "SELECT testString, testInt, testDouble, testDate FROM testTableName",
                new SelectQuery(
                        table.getTableName().name(),
                        args
                ).toString()
        );
    }

    @Test
    public void createSelectQueryWithWhereClause() {
        String[] args = new String[table.getColumnNameList().size()];
        args = table.getColumnNameList().toArray(args);
        assertEquals(
                "SELECT testString, testInt, testDouble, testDate FROM testTableName " +
                        "WHERE testString = ? AND testInt = ? AND testDouble = ? AND testDate = ?",
                new SelectQuery(
                        table.getTableName().name(),
                        new WhereClause(args),
                        args
                ).toString()
        );
    }

    @Test
    public void createVanillaDeleteQuery() {
        assertEquals(
                "DELETE FROM testTableName WHERE id = ?",
                new DeleteQuery(
                        table.getTableName().name(),
                        new WhereClause(
                                new String[]{table.getId().idColumnName()}
                                )
                ).toString()
        );
    }

    @Test
    public void createOrderByClauseSingleCondition() {
        assertEquals(
                " ORDER BY testString",
                new OrderByClause(
                        new String[]{table.getColumnNameList().get(0)}
                        ).toString()
        );
    }

    @Test
    public void createOrderByClauseMultipleCondition() {
        assertEquals(
                " ORDER BY testString, testInt",
                new OrderByClause(
                        new String[]{
                                table.getColumnNameList().get(0),
                                table.getColumnNameList().get(1)
                        }
                ).toString()
        );
    }
}