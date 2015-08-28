package net.muneris.simbadder.model;

import static net.muneris.simbadder.testutils.TestConstants.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class CooTest {

    Coo coo = new Coo();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCoo() {
        assertNotNull(coo);
    }

    @Test
    public void testGetWavelengthType() {
        coo.setWavelengthType(STRING);
        assertEquals(STRING, coo.getWavelengthType());
    }

}
