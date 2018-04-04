import libraries.orm.crud.relationaldatabase.CrudableFactoryTest;
import libraries.orm.crud.relationaldatabase.databasemanagement.CreateTableTest;
import libraries.orm.crud.relationaldatabase.databasemanagement.DatabaseMetadataTest;
import libraries.orm.crud.relationaldatabase.databasemanagement.DropTableTest;
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
        CRUDTest.class,
        CrudableFactoryTest.class,
        CreateTableTest.class,
        DatabaseMetadataTest.class,
        DropTableTest.class
})
public class TestSuite {}
