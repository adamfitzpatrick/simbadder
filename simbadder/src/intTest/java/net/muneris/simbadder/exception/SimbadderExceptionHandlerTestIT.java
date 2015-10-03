package net.muneris.simbadder.exception;

import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_FORMATTING_ERROR;
import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_PARSE_ERROR;
import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_UNEXPECTED_ERROR;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import net.muneris.simbadder.SpringAwareContextIT;

import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@DirtiesContext
public class SimbadderExceptionHandlerTestIT
                            extends SpringAwareContextIT {
    
    MockRestServiceServer mockServer;
    
    @Autowired
    RestTemplate restTemplate;
    
    @Before
    public void setUp() throws Exception {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSimbadParseException() {
        mockServer.expect(anything())
            .andRespond(withSuccess(SIMBAD_PARSE_ERROR, MediaType.TEXT_PLAIN));
        when().get("id/id").then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body("reason", is(SimbadExceptionClass.PARSE_ERROR.toString()));
    }
    
    @Test
    public void testSimbadFormattingException() {
        mockServer.expect(anything())
            .andRespond(withSuccess(SIMBAD_FORMATTING_ERROR, MediaType.TEXT_PLAIN));
        when().get("id/id").then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("reason", is(SimbadExceptionClass.FORMATTING_ERROR.toString()));
    }
    
    @Test
    public void testSimbadUnspecifiedException() {
        mockServer.expect(anything())
            .andRespond(withSuccess(SIMBAD_UNEXPECTED_ERROR, MediaType.TEXT_PLAIN));
        when().get("id/id").then()
            .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
            .body("reason", is(SimbadExceptionClass.UNSPECIFIED_ERROR.toString()));
    }
    
    @Test
    public void testUnreadableUnitException() {
        when().get("id/hd1/around?radius=1&unit=Q").then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("source", is("Controller.getAroundId"))
            .and().body("message", is("Unable to parse radius unit: Q"));
    }
    
    @Test
    public void testInvalidCoordinateException() {
        when().get("id/hd1/around?radius=1000&unit=d").then()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .body("source", is("Controller.getAroundId"))
            .body("reason", is("Coordinate out of range"));
    }
}
