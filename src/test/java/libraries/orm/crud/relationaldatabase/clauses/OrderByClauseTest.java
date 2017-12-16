package libraries.orm.crud.relationaldatabase.clauses;

import libraries.orm.crud.Condition;
import libraries.orm.orm.Table;
import org.junit.jupiter.api.Test;
import pojo.POJOWithData;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderByClauseTest {
    @Test
    public void soloOrderByClauseCanBeCreated() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Condition> tableMap = Table.getColumnAndValueList(POJOWithData.getPojoWithAnnotationsPrimary());
        ArrayList<Condition> parameters = new ArrayList<>();
        parameters.add(
                new Condition(
                        "testString", tableMap.stream().filter(p -> p.getColumnName().equals("testString")).findFirst().get())
        );
        assertEquals(
                " ORDER BY testString",
                new OrderByClause(
                        parameters
                ).toString()
        );
    }

    @Test
    public void soloOrderByClauseWithMultipleColumnsCanBeCreated() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Condition> conditions = new ArrayList<>();
        conditions.add(
                new Condition("testString", null));
        conditions.add(
                new Condition("testInt", null));
        assertEquals(
                " ORDER BY testString, testInt",
                new OrderByClause(
                        conditions
                ).toString()
        );
    }
}
