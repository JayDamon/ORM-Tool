package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.SetupTestQueryParameters;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import libraries.orm.orm.Table;
import org.junit.jupiter.api.Test;
import pojo.POJOWithAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteQueryTest extends SetupTestQueryParameters {
    @Test
    public void minimalDeleteQueryCreated() {
        assertEquals(
                "DELETE FROM testTableName WHERE id = ?",
                new DeleteQuery(
                        Table.getTableName(POJOWithAnnotations.class).name(),
                        new WhereClause(
                                expectedConditions
                        )
                ).toString()
        );
    }
}
