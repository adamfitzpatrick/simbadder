package net.muneris.simbadder.exception;

import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_FORMATTING_ERROR;
import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_PARSE_ERROR;

import com.fasterxml.jackson.databind.JsonMappingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class SimbadExceptionResponseHandlerTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSimbadErrorResponseHandlerJsonMappingExceptionFormattingException() {
        exception.expect(SimbadFormattingException.class);
        String message = SimbadException.FORMATTING_EXCEPTION + ": bad field";
        exception.expectMessage(message);
        throw new SimbadExceptionResponseHandler(new JsonMappingException(SIMBAD_FORMATTING_ERROR));
    }

    @Test
    public void testSimbadErrorResponseHandlerJsonMappingExceptionParseException() {
        exception.expect(SimbadParseException.class);
        exception.expectMessage("error message: foo");
        throw new SimbadExceptionResponseHandler(new JsonMappingException(SIMBAD_PARSE_ERROR));
    }

    @Test
    public void testSimbadErrorResponseHandlerJsonParseExceptionString() {
    }

}
