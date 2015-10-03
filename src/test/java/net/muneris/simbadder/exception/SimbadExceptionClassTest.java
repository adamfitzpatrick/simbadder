package net.muneris.simbadder.exception;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.http.HttpStatus;

public class SimbadExceptionClassTest {

    SimbadExceptionClass eClass;

    @Test
    public void testFactory() {
        assertThat(SimbadExceptionClass.factory("Incorrect Field In Format"),
                is(SimbadExceptionClass.FORMATTING_ERROR));
        assertThat(SimbadExceptionClass.factory("ParseException"),
                is(SimbadExceptionClass.PARSE_ERROR));
        assertThat(SimbadExceptionClass.factory("Unspecified Error"),
                is(SimbadExceptionClass.UNSPECIFIED_ERROR));
        assertThat(SimbadExceptionClass.factory("foo"),
                is(SimbadExceptionClass.UNSPECIFIED_ERROR));
    }

    @Test
    public void testToString() {
        assertThat(SimbadExceptionClass.FORMATTING_ERROR.toString(),
                is("Incorrect Field In Format"));
        assertThat(SimbadExceptionClass.PARSE_ERROR.toString(),
                is("ParseException"));
        assertThat(SimbadExceptionClass.UNSPECIFIED_ERROR.toString(),
                is("Unspecified Error"));
    }

    @Test
    public void testGetStatus() {
        assertThat(SimbadExceptionClass.FORMATTING_ERROR.getStatus(),
                is(HttpStatus.BAD_REQUEST));
        assertThat(SimbadExceptionClass.PARSE_ERROR.getStatus(),
                is(HttpStatus.NOT_FOUND));
        assertThat(SimbadExceptionClass.UNSPECIFIED_ERROR.getStatus(),
                is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
