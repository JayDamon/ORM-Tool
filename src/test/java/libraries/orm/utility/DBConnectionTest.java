package libraries.orm.utility;

import libraries.orm.crud.InsertQuery;
import libraries.orm.crud.Query;
import libraries.orm.orm.Table;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pojo.POJOWithAnnotations;

import java.sql.*;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

/**
 * Created by Jay Damon on 11/10/2017.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBConnectionTest {
    @InjectMocks private DBConnection dbConnection;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;
    @Mock private PreparedStatement mockPreparedStatement;
    private POJOWithAnnotations pojo;

    @BeforeAll
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pojo = new POJOWithAnnotations();
        pojo.setTestString("TestValue");
        pojo.setTestInt(20);
        pojo.setTestDouble(30.0D);
        pojo.setTestDate(new Date(2017, 11, 5));
        mockPreparedStatement = mockConnection.prepareStatement("INSERT INTO testTableName (testString, testInt, testDouble, testDate) VALUES(?, ?, ?, ?)");
    }

    @Test
    public void testMockDBConnection() throws Exception {
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
        int value = dbConnection.executeQuery("");
        Assert.assertEquals(value, 1);
        Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }
}
