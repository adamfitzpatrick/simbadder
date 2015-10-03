package net.muneris.simbadder;

import static com.jayway.restassured.RestAssured.when;

import org.apache.http.HttpStatus;
/**
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class StepDefinitions {

    public void somethingHappens() {
        when().get("id/hd1").then().statusCode(HttpStatus.SC_OK);
    }
}
