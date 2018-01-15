package libraries.orm.crud.relationaldatabase.clauses;

import libraries.orm.crud.Condition;
import libraries.orm.crud.relationaldatabase.SetupTestQueryParameters;
import libraries.orm.orm.Table;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;
import pojo.POJOWithData;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class WhereClauseTest extends SetupTestQueryParameters {
    @Test
    public void whereClauseCreatedWithExpectedConditionsAsVariable() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Condition> conditions = new WhereClause(
                new ArrayList<>(
                        Collections.singletonList(Table.getIDColumnAndValue(pojo))
                )).getConditions();
        assertEquals(conditions.get(0).getColumnName(), expectedConditions.get(0).getColumnName());
        assertEquals(conditions.get(0).getCondition(), expectedConditions.get(0).getCondition());
    }
}
