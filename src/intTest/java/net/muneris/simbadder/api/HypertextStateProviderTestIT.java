/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
package net.muneris.simbadder.api;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import net.muneris.simbadder.SpringAwareContextIT;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;

import org.junit.Test;

/**
 * @author adam.fitzpatrick
 *
 */
public class HypertextStateProviderTestIT extends SpringAwareContextIT {

    @Test
    public void testAddObjectSelfRel() {
        assertEquals(true, true);
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
