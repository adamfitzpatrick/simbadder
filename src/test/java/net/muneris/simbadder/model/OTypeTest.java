package net.muneris.simbadder.model;

import static net.muneris.simbadder.testutils.TestConstants.STRING;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class OTypeTest {

    OType oType;

    @Before
    public void setUp() throws Exception {
        oType = new OType();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetNumeric() {
        oType.setNumeric(STRING);
        assertEquals(STRING, oType.getNumeric());
    }

    @Test
    public void testGetShortName() {
        oType.setShortName(STRING);
        assertEquals(STRING, oType.getShortName());
    }

    @Test
    public void testGetVerbose() {
        oType.setVerbose(STRING);
        assertEquals(STRING, oType.getVerbose());
    }

    @Test
    public void testGetVeryShortName() {
        oType.setVeryShortName(STRING);
        assertEquals(STRING, oType.getVeryShortName());
    }

}
