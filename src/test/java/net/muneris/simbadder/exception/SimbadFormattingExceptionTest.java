package net.muneris.simbadder.exception;

import static net.muneris.simbadder.testutils.TestConstants.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class SimbadFormattingExceptionTest {

    SimbadFormattingException exception = new SimbadFormattingException(STRING);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetFullName() {
        assertEquals(SimbadException.FORMATTING_EXCEPTION, exception.getFullName());
    }

    @Test
    public void testGetName() {
        assertEquals(SimbadException.FORMATTING_EXCEPTION, exception.getName());
    }

    @Test
    public void testSimbadFormattingException() {
        assertNotNull(exception);
        String message = SimbadException.FORMATTING_EXCEPTION + ": " + STRING;
        assertEquals(message, exception.getMessage());
    }

}
