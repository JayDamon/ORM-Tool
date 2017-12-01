package libraries.orm.crud.relationaldatabase.query;

import libraries.orm.crud.relationaldatabase.SetupTestQueryParameters;
import libraries.orm.crud.relationaldatabase.clauses.WhereClause;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteQueryTest extends SetupTestQueryParameters {
    @Test
    public void minimalDeleteQueryCreated() {
        assertEquals(
                "DELETE FROM testTableName WHERE id = ?",
                new DeleteQuery(
                        table.getTableName().name(),
                        new WhereClause(
                                expectedConditions
                        )
                ).toString()
        );
    }
}
