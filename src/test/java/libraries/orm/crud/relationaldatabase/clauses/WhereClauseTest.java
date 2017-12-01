package libraries.orm.crud.relationaldatabase.clauses;

import libraries.orm.crud.relationaldatabase.SetupTestQueryParameters;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class WhereClauseTest extends SetupTestQueryParameters {
    @Test
    public void whereClauseCreatedWithExpectedConditionsAsVariable() throws InvocationTargetException, IllegalAccessException {

        assertThat(
                new WhereClause(table.getIDColumnAndValue()).getConditions(),
                is(expectedConditions)
        );
    }
}
