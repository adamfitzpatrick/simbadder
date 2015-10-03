package net.muneris.simbadder.simbadapi.query;

import static net.muneris.simbadder.testutils.TestConstants.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class CustomQueryTest {

    CustomQuery query;

    @Before
    public void setUp() throws Exception {
        query = new CustomQuery(STRING);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCustomQuery() {
        assertNotNull(query);
    }

    @Test
    public void testGetQueryString() {
        assertEquals(STRING, query.getQueryString());
    }

}
