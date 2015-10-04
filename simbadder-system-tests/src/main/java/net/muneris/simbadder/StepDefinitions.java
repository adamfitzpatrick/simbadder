package net.muneris.simbadder;

import static com.jayway.restassured.RestAssured.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;

import org.apache.http.HttpStatus;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class StepDefinitions {

    private static final String IDQUERY = "/id/sirius";
    private static final String SELF_LINK = "http://localhost:7901/id/*%20alf%20CMa";
    
    private JsonPath jsonResponse;
    
    @Before
    public void setup() {
        RestAssured.port = 7901;
    }
    
    @When("^I retrieve data on the star Sirius$")
    public void iRetrieveDataOnTheStarSirius() {
        String response = when().get(IDQUERY)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().asString();
        jsonResponse = new JsonPath(response);
    }
    
    @Then("^I can obtain a unique self-reference link to Sirius$")
    public void iCanObtainAUniqueSelfReferenceLinkToSirius() {
        List<Map<String, String>> jsonList = parseJsonArray(jsonResponse, "links");
        assertThat(jsonList.size(), is(1));
        assertThat(jsonList.get(0).get("rel"), is("self"));
        assertThat(jsonList.get(0).get("href"), is(SELF_LINK));
    }
    
    private List<Map<String, String>> parseJsonArray(JsonPath json, String object) {
        int size = json.getInt(object + ".size()");
        if (size == 0) {
            return null;
        }
        List<Map<String, String>> jsonList = new ArrayList<>();
        for (int k = 0; k < size; k++) {
            jsonList.add(json.getMap(object + "[" + k + "]", String.class, String.class));
        }
        return jsonList;
    }
}
