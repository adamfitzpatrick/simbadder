package net.muneris.simbadder.api;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import net.muneris.simbadder.SpringAwareContextIT;
import net.muneris.simbadder.model.SimbadObject;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;

import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */

public class ControllerTestIT extends SpringAwareContextIT {

    private final String hd1Id = "HD      1";
    private final String hd2Id = "HD      2";
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
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
    public void testGetAroundIdUnreadableUnit() {
        when().get("id/hd1/around?radius=1&unit=q").then().contentType(ContentType.JSON)
            .body("reason", is("Convert string to RadiusUnit"))
            .body("source", is("Controller.getAroundId"));
    }
    
    @Test
    public void testGetAroundIdInvalidRadius() {
        when().get("id/hd1/around?radius=1000").then().contentType(ContentType.JSON)
            .body("reason", is("Coordinate out of range"))
            .body("source", is("Controller.getAroundId"));
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
    
    @Test
    public void testGetForCoo() {
        String response = when()
                .get("coo?ra=0&dec=0&radius=5&unit=m&epoch=2000&equi=2000")
                .then().contentType(ContentType.JSON)
                .extract().response().asString();
        JsonPath json = new JsonPath(response);
        assertThat(json.getInt("objects.size()"), is(8));
        assertThat(json.get("objects[0].mainId"), is("SDSS J000004.21+000122.4"));
    }
    
    @Test
    public void testGetForCooUnreadableUnit() {
        when().get("coo?ra=0&dec=0&radius=5&unit=q&epoch=2000&equi=2000")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST).contentType(ContentType.JSON)
                .body("reason", is("Convert string to RadiusUnit"))
                .body("source", is("Controller.getForCooQuery"));
    }
    
    @Test
    public void testGetForCooInvalidRa() {
        when().get("coo?ra=1000&dec=0&radius=5&unit=m&epoch=2000&equi=2000")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST).contentType(ContentType.JSON)
                .body("reason", is("Coordinate out of range"))
                .body("source", is("Controller.getForCooQuery"));
    }
    
    @Test
    public void testGetForCooInvalidDec() {
        when().get("coo?ra=0&dec=1000&radius=5&unit=m&epoch=2000&equi=2000")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST).contentType(ContentType.JSON)
                .body("reason", is("Coordinate out of range"))
                .body("source", is("Controller.getForCooQuery"));
    }
    
    @Test
    public void testGetForCooInvalidRadius() {
        when().get("coo?ra=0&dec=0&radius=1000&unit=d&epoch=2000&equi=2000")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST).contentType(ContentType.JSON)
                .body("reason", is("Coordinate out of range"))
                .body("source", is("Controller.getForCooQuery"));
    }
}
