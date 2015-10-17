package net.muneris.simbadder.simbadapi.query.sample;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import net.muneris.simbadder.exception.SimbadderException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SampleQueryTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    SampleQuery query;
    Map<String, Map<String, String>> requestBody;
    
    @Before
    public void setUp() throws Exception {
        Map<String, String> criterion = new HashMap<>();
        criterion.put("$gt", "1");
        requestBody = new HashMap<>();
        requestBody.put("ra", criterion);
        query = new SampleQuery(requestBody);
    }

    @Test
    public void testSampleQuery() {
        assertThat(query, is(notNullValue()));
    }
    
    @Test
    public void testSampleQueryInvalid() {
        exception.expect(SimbadderException.class);
        Map<String, String> criterion = new HashMap<>();
        criterion.put("$gt", "1");
        Map<String, Map<String, String>> badRequest = new HashMap<>();
        badRequest.put("foo", criterion);
        query = new SampleQuery(badRequest);
    }

    @Test
    public void testGetQuery() {
        assertThat(query.getQuery(), is(requestBody));
    }

    @Test
    public void testGetQueryString() {
        String expected = "query sample ra > 1";
        assertThat(query.getQueryString(), is(expected));
    }

}
