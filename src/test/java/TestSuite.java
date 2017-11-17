import libraries.orm.crud.InsertPreparedStatementTestWithCompletePOJO;
import libraries.orm.orm.TableTest;
import libraries.orm.utility.DBConnectionTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        InsertPreparedStatementTestWithCompletePOJO.class,
        TableTest.class,
        DBConnectionTest.class
})
public class TestSuite {
//    public TestSuite() {}
}
