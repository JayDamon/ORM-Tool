package libraries.orm.crud.relationaldatabase.clauses;

import libraries.orm.orm.Table;
import org.junit.jupiter.api.Test;
import pojo.POJOWithData;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderByClauseTest {
    @Test
    public void soloOrderByClauseCanBeCreated() throws InvocationTargetException, IllegalAccessException {
        Table table = new Table(POJOWithData.getPojoWithAnnotationsPrimary());
        LinkedHashMap<String, Object> tableMap = table.getColumnAndValueList();
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("testString", tableMap.get("testString"));
        assertEquals(
                " ORDER BY testString",
                new OrderByClause(
                        parameters
                ).toString()
        );
    }

    @Test
    public void soloOrderByClauseWithMultipleColumnsCanBeCreated() throws InvocationTargetException, IllegalAccessException {
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("testString", linkedHashMap.get("testString"));
        linkedHashMap.put("testInt", linkedHashMap.get("testInt"));
        assertEquals(
                " ORDER BY testString, testInt",
                new OrderByClause(
                        linkedHashMap
                ).toString()
        );
    }
}
