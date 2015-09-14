package net.muneris.simbadder.exception;

import static net.muneris.simbadder.testutils.TestConstants.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IdQueryExceptionTest {

    IdQueryException exception = new IdQueryException(STRING);
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testIdQueryException() {
        assertNotNull(exception);
    }

    @Test
    public void testGetMessage() {
        assertEquals(STRING, exception.getMessage());
    }

}
