package libraries.orm.crud.relationaldatabase;

import libraries.orm.orm.Table;
import org.junit.jupiter.api.BeforeAll;
import pojo.POJOWithAnnotations;
import pojo.POJOWithData;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

public class SetupTestQueryParameters {
    protected static Table table;
    protected static String[] columnNames;
    protected static LinkedHashMap<String, Object> columnNamesAndValues;
    protected static LinkedHashMap<String, Object> expectedConditions;
    protected static LinkedHashMap<String, Object> expectedColumnNamesAndValues;

    @BeforeAll
    static void setup() throws InvocationTargetException, IllegalAccessException {
        POJOWithAnnotations pojo = POJOWithData.getPojoWithAnnotationsPrimary();
        pojo.setId(2);

        table = new Table(pojo);
        columnNames = new String[table.getColumnNameList().size()];
        columnNames = table.getColumnNameList().toArray(columnNames);

        columnNamesAndValues = table.getColumnAndValueList();

        expectedConditions = new LinkedHashMap<>();
        expectedConditions.put("id", 2);

        expectedColumnNamesAndValues = new LinkedHashMap<>();
        expectedColumnNamesAndValues.put("testString", "TestValue");
        expectedColumnNamesAndValues.put("testInt", 20);
        expectedColumnNamesAndValues.put("testDouble", 30.0D);
        expectedColumnNamesAndValues.put("testDate", pojo.getTestDate());
    }
}
