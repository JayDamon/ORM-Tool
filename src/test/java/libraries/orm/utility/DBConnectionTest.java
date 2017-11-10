package libraries.orm.utility;

import junit.framework.TestCase;
import libraries.orm.crud.InsertQuery;
import libraries.orm.crud.Query;
import libraries.orm.orm.Table;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pojo.POJOWithAnnotations;

import java.sql.*;

/**
 * Created by Jay Damon on 11/10/2017.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBConnectionTest {
    @InjectMocks private DBConnection dbConnection;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;
    private PreparedStatement statement;
    private POJOWithAnnotations pojo;

    @BeforeAll
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pojo = new POJOWithAnnotations();
        pojo.setTestString("TestValue");
        pojo.setTestInt(20);
        pojo.setTestDouble(30.0D);
        pojo.setTestDate(new Date(2017, 11, 5));
        statement = mockConnection.prepareStatement("\"INSERT INTO testTableName (testString, testInt, testDouble, testDate) VALUES(?, ?, ?, ?)");
//        statement.set
    }

    @Test
    public void testMockDBConnection() throws Exception {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
        int value = dbConnection.executeQuery("");
        Assert.assertEquals(value, 1);
        Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }

    //https://stackoverflow.com/questions/28388204/how-to-test-dao-methods-using-mockito TODO stackoverflow post on how to fix test
    @Test void createSuccessfullPreparedStatement() throws SQLException {
        Query query = new InsertQuery();
        PreparedStatement stmt = query.createQuery(mockConnection, new Table(new POJOWithAnnotations()));
        stmt.setInt(1, 1);
        Assert.assertEquals(statement, stmt);
    }
}
