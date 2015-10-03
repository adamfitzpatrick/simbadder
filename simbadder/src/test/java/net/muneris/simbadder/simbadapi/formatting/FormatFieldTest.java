package net.muneris.simbadder.simbadapi.formatting;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class FormatFieldTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testValueOf() {
        assertEquals(
                "mainOType: { %OTYPE(numeric: !`!N!`!, shortname: !`!S!`!, veryshortname: !`!3!`!, "
                        + "verbose: !`!V!`!) }", FormatField.MAINOTYPE.valueOf());
    }

}
