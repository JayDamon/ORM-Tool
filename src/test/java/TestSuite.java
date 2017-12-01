import libraries.orm.crud.relationaldatabase.query.*;
import libraries.orm.crud.relationaldatabase.clauses.*;
import libraries.orm.orm.TableTest;
import libraries.orm.orm.TableTestPOJOWithAnnotations;
import libraries.orm.utility.CRUDTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        TableTest.class,
        SelectQueryTest.class,
        UpdateQueryTest.class,
        InsertQueryTest.class,
        DeleteQueryTest.class,
        OrderByClauseTest.class,
        WhereClauseTest.class,
        TableTestPOJOWithAnnotations.class,
        CRUDTest.class
})

public class TestSuite {}
