package net.muneris.simbadder.exception;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.http.HttpStatus;

public class SimbadExceptionClassTest {

    private ExceptionClass exceptionClass;

    @Test
    public void testFactory() {
        exceptionClass = ExceptionClass.factory("incorrect field in format");
        assertThat(exceptionClass, is(ExceptionClass.FORMATTING_ERROR));
        exceptionClass = ExceptionClass.factory("ParseException");
        assertThat(exceptionClass, is(ExceptionClass.PARSE_ERROR));
        exceptionClass = ExceptionClass.factory("foo");
        assertThat(exceptionClass, is(ExceptionClass.UNSPECIFIED_ERROR));
    }

    @Test
    public void testToString() {
        exceptionClass = ExceptionClass.FORMATTING_ERROR;
        assertThat(exceptionClass.toString(), is("Incorrect Field In Format"));
        exceptionClass = ExceptionClass.PARSE_ERROR;
        assertThat(exceptionClass.toString(), is("ParseException"));
        exceptionClass = ExceptionClass.UNSPECIFIED_ERROR;
        assertThat(exceptionClass.toString(), is("Unspecified Error"));
    }

    @Test
    public void testGetStatus() {
        exceptionClass = ExceptionClass.FORMATTING_ERROR;
        assertThat(exceptionClass.getStatus(), is(HttpStatus.BAD_REQUEST));
        exceptionClass = ExceptionClass.PARSE_ERROR;
        assertThat(exceptionClass.getStatus(), is(HttpStatus.NOT_FOUND));
        exceptionClass = ExceptionClass.UNSPECIFIED_ERROR;
        assertThat(exceptionClass.getStatus(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
