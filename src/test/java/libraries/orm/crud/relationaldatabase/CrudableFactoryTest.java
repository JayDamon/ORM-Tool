package libraries.orm.crud.relationaldatabase;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CrudableFactoryTest {

    static POJOWithAnnotations resultPojo;

    @BeforeAll
    public static void setup() {
        Map<String, Object> resultSet = new HashMap<>();
        resultSet.put("TESTSTRING", "string value");
        resultSet.put("TESTINT", 5);
        resultSet.put("TESTDOUBLE", 2.2);

        resultPojo = CrudableFactory.getCrudable(POJOWithAnnotations.class, resultSet);
    }

    @Test
    public void getCrudableStringFromMap() {
        assertEquals("string value", resultPojo.getTestString());
    }

    @Test
    public void getCrudableIntFromMap() {
        assertEquals(5, resultPojo.getTestInt());
    }

    @Test
    public void getCrudableDoubleFromMap() {
        assertEquals(2.2, resultPojo.getTestDouble());
    }
}