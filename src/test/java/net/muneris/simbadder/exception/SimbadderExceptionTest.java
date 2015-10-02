package net.muneris.simbadder.exception;

import static net.muneris.simbadder.testutils.TestConstants.NAME;
import static net.muneris.simbadder.testutils.TestConstants.STRING;
import static net.muneris.simbadder.testutils.TestConstants.STRING2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

public class SimbadderExceptionTest {

    private SimbadderException ex;
    private Long expectedTime;
    
    @Before
    public void setUp() throws Exception {
        ex = new SimbadderException(STRING, STRING2, NAME, HttpStatus.OK);
        expectedTime = (Long) ReflectionTestUtils.getField(ex, "timestamp");
    }

    @Test
    public void testSimbadderExceptionSimbadExceptionClassStringString() {
        SimbadExceptionClass exClass = SimbadExceptionClass.PARSE_ERROR;
        ex = new SimbadderException(exClass, STRING, STRING2);
        assertThat(ex.getReason(), is(exClass.toString()));
        assertThat(ex.getMessage(), is(STRING));
        assertThat(ex.getSource(), is(STRING2));
        assertThat(ex.getStatus(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testSimbadderExceptionStringStringStringHttpStatus() {
        assertThat(ex, is(not(nullValue())));
    }

    @Test
    public void testGetTimestamp() {
        assertThat(ex.getTimestamp(), is(expectedTime));
    }

    @Test
    public void testGetSource() {
        assertThat(ex.getSource(), is(NAME));
    }

    @Test
    public void testGetReason() {
        assertThat(ex.getReason(), is(STRING));
    }

    @Test
    public void testGetStatusValue() {
        assertThat(ex.getStatusValue(), is(HttpStatus.OK.value()));
    }

    @Test
    public void testGetError() {
        assertThat(ex.getError(), is(HttpStatus.OK.getReasonPhrase()));
    }

    @Test
    public void testGetMessage() {
        assertThat(ex.getMessage(), is(STRING2));
    }

    @Test
    public void testGetStatus() {
        assertThat(ex.getStatus(), is(HttpStatus.OK));
    }

    @Test
    public void testEqualsObject() {
        SimbadderException ex2 = new SimbadderException(NAME, STRING, STRING2,
                HttpStatus.BAD_REQUEST);
        assertThat(ex.equals(ex2), is(false));
        ex2 = new SimbadderException(STRING, STRING, STRING2, HttpStatus.BAD_REQUEST);
        assertThat(ex.equals(ex2), is(false));
        ex2 = new SimbadderException(STRING, STRING2, STRING2, HttpStatus.BAD_REQUEST);
        assertThat(ex.equals(ex2), is(false));
        ex2 = new SimbadderException(STRING, STRING2, NAME, HttpStatus.BAD_REQUEST);
        assertThat(ex.equals(ex2), is(false));
        ex2 = new SimbadderException(STRING, STRING2, NAME, HttpStatus.OK);
        assertThat(ex.equals(ex2), is(true));
        Exception ex3 = new Exception();
        assertThat(ex.equals(ex3), is(false));
    }

}
