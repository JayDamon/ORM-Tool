package libraries.orm.crud.relationaldatabase;

import libraries.orm.crud.Condition;
import libraries.orm.orm.Table;
import org.junit.jupiter.api.BeforeAll;
import pojo.POJOWithAnnotations;
import pojo.POJOWithData;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class SetupTestQueryParameters {
    protected static POJOWithAnnotations pojo;
    protected static String[] columnNames;
    protected static ArrayList<Condition> columnNamesAndValues;
    protected static ArrayList<Condition> expectedConditions;
    protected static ArrayList<Condition> expectedColumnNamesAndValues;

    @BeforeAll
    static void setup() throws InvocationTargetException, IllegalAccessException {
        pojo = POJOWithData.getPojoWithAnnotationsPrimary();
        pojo.setId(2);

        columnNames = new String[Table.getColumnNameList(pojo.getClass()).size()];
        columnNames = Table.getColumnNameList(pojo.getClass()).toArray(columnNames);

        columnNamesAndValues = Table.getColumnAndValueList(pojo);

        expectedConditions = new ArrayList<>();
        expectedConditions.add(
                new Condition("id", 2)
        );

        expectedColumnNamesAndValues = new ArrayList<>();
        expectedColumnNamesAndValues.add(
                new Condition("testString", "TestValue"));
        expectedColumnNamesAndValues.add(
                new Condition("testInt", 20));
        expectedColumnNamesAndValues.add(
                new Condition("testDouble", 30.0D));
        expectedColumnNamesAndValues.add(
                new Condition("testDate", pojo.getTestDate()));
    }
}
