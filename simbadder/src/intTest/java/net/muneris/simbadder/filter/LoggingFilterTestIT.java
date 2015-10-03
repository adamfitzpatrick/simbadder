package net.muneris.simbadder.filter;

import static net.muneris.simbadder.testutils.PatternMatcher.matches;
import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_RESPONSE_STRING;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import net.muneris.simbadder.Application;
import net.muneris.simbadder.testutils.TestLoggingAppender;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class LoggingFilterTestIT {

    TestLoggingAppender appender;
    MockMvc mockMvc;
    
    @Autowired
    WebApplicationContext context;
    
    @Autowired
    LoggingFilter filter;
    
    @Autowired
    RestTemplate restTemplate;
    
    MockRestServiceServer mockServer;
    
    @Before
    public void setUp() throws Exception {
        appender = new TestLoggingAppender();
        Logger.getRootLogger().addAppender(appender);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(filter, "/*").build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(anything())
            .andRespond(withSuccess(SIMBAD_RESPONSE_STRING, MediaType.TEXT_PLAIN));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDoFilter() throws Exception {
        mockMvc.perform(get("/id/hd1")).andExpect(status().isOk());
        assertNotNull(appender.getEventsList());
        List<LoggingEvent> events = appender.getEventByLoggerName(filter.getClass().getName());
        assertThat(events.size(), is(2));
        assertThat(events.get(0).getMessage().toString(),
                matches("^Received request for resource /id/hd1$"));
        assertThat(events.get(1).getMessage().toString(),
                matches("^Completed request for resource /id/hd1 with 200 \\(OK\\)"));
    }
}
