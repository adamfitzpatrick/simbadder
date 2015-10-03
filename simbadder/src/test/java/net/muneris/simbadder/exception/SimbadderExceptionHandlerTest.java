package net.muneris.simbadder.exception;

import static net.muneris.simbadder.testutils.TestConstants.*;
import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_PARSE_ERROR;
import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_UNEXPECTED_ERROR;
import static net.muneris.simbadder.testutils.TestConstants.STRING;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
@RunWith(EasyMockRunner.class)
public class SimbadderExceptionHandlerTest {

    private static final String SOURCE = "Relayed from SIMBAD";
    private static final String PARSEERRMESSAGE = "error message: foo";
    private static final String FORMATERRMESSAGE = "bad field";
    private static final String UNREADABLE = "No additional information from SIMBAD.";
    
    private SimbadderExceptionHandler handler;
    private SimbadderException expected;
    private SimbadderException actual;
    
    @Mock
    private ResourceAccessException accessException;
    
    @Mock
    private SimbadderException simbadderException;

    @Before
    public void setUp() throws Exception {
        handler = new SimbadderExceptionHandler();
    }
    
    @Test
    public void testResolveExceptionParseException() {
        expected =
                new SimbadderException(SimbadExceptionClass.PARSE_ERROR,
                        PARSEERRMESSAGE, SOURCE);
        expect(accessException.getMessage()).andReturn(SIMBAD_PARSE_ERROR).times(2);
        replay(accessException);
        executeAccessException();
        asserter();
    }
    
    @Test
    public void testResolveExceptionFormattingError() {
        expected =
                new SimbadderException(SimbadExceptionClass.FORMATTING_ERROR,
                        FORMATERRMESSAGE, SOURCE);
        expect(accessException.getMessage()).andReturn(SIMBAD_FORMATTING_ERROR).times(2);
        replay(accessException);
        executeAccessException();
        asserter();
    }

    private void executeAccessException() {
        ResponseEntity<SimbadderException> response = handler.resolveException(accessException);
        assertNotNull(response.getBody());
        actual = response.getBody();
    }
    
    @Test
    public void testResolveExceptionUnspecifiedError() {
        expected = new SimbadderException(SimbadExceptionClass.UNSPECIFIED_ERROR,
                UNREADABLE, SOURCE);
        expect(accessException.getMessage()).andReturn(SIMBAD_UNEXPECTED_ERROR).times(2);
        replay(accessException);
        executeAccessException();
        asserter();
    }
    
    @Test
    public void testResolveExceptionUnreadableMessage() {
        expected = new SimbadderException(SimbadExceptionClass.FORMATTING_ERROR,
                UNREADABLE, SOURCE);
        expect(accessException.getMessage())
            .andReturn(SIMBAD_FORMATTING_ERROR.substring(0, 72)).times(2);
        replay(accessException);
        executeAccessException();
        asserter();
    }
    
    @Test
    public void testResolveSimbadderException() {
        expected = new SimbadderException(NAME, STRING, STRING2, HttpStatus.BAD_REQUEST);
        replay(simbadderException);
        ResponseEntity<SimbadderException> response = handler.resolveException(expected);
        assertNotNull(response.getBody());
        actual = response.getBody();
        assertThat(actual.getMessage(), is(expected.getMessage()));
        assertThat(actual.getStatus(), is(expected.getStatus()));
        assertThat(actual.getSource(), is(expected.getSource()));
    }
    
    private void asserter() {
        //assertThat(actual, samePropertyValuesAs(expected));
        assertThat(actual.getReason(), is(expected.getReason()));
        assertThat(actual.getMessage(), is(expected.getMessage()));
        assertThat(actual.getStatus(), is(expected.getStatus()));
        assertThat(actual.getSource(), is(expected.getSource()));
        verify(accessException);
    }
}
