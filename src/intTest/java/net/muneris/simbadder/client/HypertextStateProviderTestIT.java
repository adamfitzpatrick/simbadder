/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
package net.muneris.simbadder.client;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.jayway.restassured.path.json.JsonPath;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author adam.fitzpatrick
 *
 */
public class HypertextStateProviderTestIT {

    Controller controller;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = 7901;
        RestAssuredMockMvc.standaloneSetup(new Controller());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddObjectSelfRel() {
        when().get("id/hd1").then()
                .body("links[0].href", is("http://localhost/id/HD%20%20%20%20%20%201"));
    }

    @Test
    public void testAddObjectSelfRelForList() {
        String response =
                when().get("id?in=hd1,hd2").then().contentType(ContentType.JSON).extract()
                        .response().asString();
        JsonPath json = new JsonPath(response);
        assertEquals("http://localhost/id/HD%20%20%20%20%20%201",
                json.get("objects[0].links[0].href"));
        assertEquals("http://localhost/id/HD%20%20%20%20%20%202",
                json.get("objects[1].links[0].href"));
    }

}
