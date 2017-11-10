package libraries.orm.utility;

import junit.framework.TestCase;
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

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Jay Damon on 11/10/2017.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBConnectionTest {
    @InjectMocks private DBConnection dbConnection;
    @Mock private Connection mockConnection;
    @Mock private Statement mockStatement;

    @BeforeAll
    public void setUp() throws Exception {
        System.out.println("setUp");
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMockDBConnection() throws Exception {
        System.out.println("testMethod");
        Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
        Mockito.when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
        int value = dbConnection.executeQuery("");
        Assert.assertEquals(value, 1);
        Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }
}
