package net.muneris.simbadder.exception;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.http.HttpStatus;

public class ExceptionClassTest {

    ExceptionClass eClass;

    @Test
    public void testFactory() {
        assertThat(ExceptionClass.factory("Incorrect Field In Format"),
                is(ExceptionClass.FORMATTING_ERROR));
        assertThat(ExceptionClass.factory("ParseException"),
                is(ExceptionClass.PARSE_ERROR));
        assertThat(ExceptionClass.factory("IdQueryException"),
                is(ExceptionClass.IDQUERY_EXCEPTION));
        assertThat(ExceptionClass.factory("Unspecified Error"),
                is(ExceptionClass.UNSPECIFIED_ERROR));
    }

    @Test
    public void testToString() {
        assertThat(ExceptionClass.FORMATTING_ERROR.toString(),
                is("Incorrect Field In Format"));
        assertThat(ExceptionClass.PARSE_ERROR.toString(),
                is("ParseException"));
        assertThat(ExceptionClass.IDQUERY_EXCEPTION.toString(),
                is("IdQueryException"));
        assertThat(ExceptionClass.UNSPECIFIED_ERROR.toString(),
                is("Unspecified Error"));
    }

    @Test
    public void testGetStatus() {
        assertThat(ExceptionClass.FORMATTING_ERROR.getStatus(),
                is(HttpStatus.BAD_REQUEST));
        assertThat(ExceptionClass.PARSE_ERROR.getStatus(),
                is(HttpStatus.NOT_FOUND));
        assertThat(ExceptionClass.IDQUERY_EXCEPTION.getStatus(),
                is(HttpStatus.BAD_REQUEST));
        assertThat(ExceptionClass.UNSPECIFIED_ERROR.getStatus(),
                is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
