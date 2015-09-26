package net.muneris.simbadder.filter;

import static net.muneris.simbadder.testutils.PatternMatcher.matches;
import static net.muneris.simbadder.testutils.TestConstants.STRING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import net.muneris.simbadder.testutils.TestLoggingAppender;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class LoggingFilterTest {

    LoggingFilter filter;
    TestLoggingAppender appender;
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    MockFilterChain chain;
    
    @Before
    public void setUp() throws Exception {
        filter = new LoggingFilter();
        appender = new TestLoggingAppender();
        Logger.getRootLogger().addAppender(appender);
        request = new MockHttpServletRequest();
        request.setRequestURI(STRING);
        response = new MockHttpServletResponse();
        response.setStatus(HttpStatus.OK.value());
        chain = new MockFilterChain();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDoFilter() throws IOException, ServletException {
        filter.doFilter(request, response, chain);
        assertNotNull(appender.getEventsList());
        assertThat(appender.getEventsList().size(), is(2));
        assertThat(appender.getEventsList().get(0).getLevel(), is(Level.INFO));
        assertThat(appender.getEventsList().get(1).getLevel(), is(Level.INFO));
        
        List<String> messages = appender.getMessages();
        assertThat(messages.get(0), matches("^Received request for resource "
                + "test string$"));
        assertThat(messages.get(1), matches("^Completed request for resource "
                + "test string with 200 \\(OK\\)"));
    }

}
