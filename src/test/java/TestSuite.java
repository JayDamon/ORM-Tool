import libraries.orm.crud.InsertStatementTestWithCompletePOJO;
import libraries.orm.orm.TableTest;
import libraries.orm.orm.TableTestPOJOWithAnnotations;
import libraries.orm.utility.DBConnectionTest;
import libraries.orm.crud.relationaldatabase.query.QueryTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        InsertStatementTestWithCompletePOJO.class,
        TableTest.class,
        DBConnectionTest.class,
        QueryTest.class,
        TableTestPOJOWithAnnotations.class
})
public class TestSuite {
}
