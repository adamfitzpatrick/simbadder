package net.muneris.simbadder.api;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import net.muneris.simbadder.SpringAwareContextIT;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */

public class ControllerTestIT extends SpringAwareContextIT {

    public final String hd1Id = "HD      1";
    public final String hd2Id = "HD      2";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAroundId() {
        when().get("id/hd1/around?radius=1&unit=m").then().contentType(ContentType.JSON)
                .body("objects.size()", is(1));
        when().get("id/hd1/around?radius=10&unit=m").then().contentType(ContentType.JSON)
                .body("objects.size()", is(15));
    }

    @Test
    public void testGetForCustomQuery() {
        String response =
                when().get("custom-query/query id hd1").then().contentType(ContentType.JSON)
                        .extract().response().asString();
        JsonPath json = new JsonPath(response);
        assertEquals(1, json.getInt("objects.size()"));
        assertEquals(hd1Id, json.get("objects[0].mainId"));
    }

    @Test
    public void testGetForId() {
        when().get("/id/hd1").then().body("mainId", is(hd1Id));
    }

    @Test
    public void testGetForIdListQuery() {
        String response =
                when().get("id?in=hd1,hd2").then().contentType(ContentType.JSON).extract()
                        .response().asString();
        JsonPath json = new JsonPath(response);
        assertEquals(2, json.getInt("objects.size()"));
        if (json.get("objects[0].mainId").equals(hd1Id)) {
            assertEquals(hd1Id, json.get("objects[0].mainId"));
            assertEquals(hd2Id, json.get("objects[1].mainId"));
        } else {
            assertEquals(hd1Id, json.get("objects[1].mainId"));
            assertEquals(hd2Id, json.get("objects[0].mainId"));
        }
    }
}
