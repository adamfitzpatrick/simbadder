package net.muneris.simbadder.model;

import static net.muneris.simbadder.testutils.TestConstants.STRING;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

public class DimensionsTest {

    Dimensions dims;
    
    @Before
    public void setUp() throws Exception {
        dims = new Dimensions();
    }

    @Test
    public void testGetSetBibCode() {
        dims.setBibCode(STRING);
        assertThat(dims.getBibCode(), is(STRING));
    }

    @Test
    public void testGetSetInclinationCode() {
        dims.setInclinationCode("0");
        assertThat(dims.getInclinationCode(), is(0));
        dims.setInclinationCode(STRING);
        assertThat(dims.getInclinationCode(), is(0));
    }

    @Test
    public void testGetSetMainAxis() {
        dims.setMainAxis("1.1");
        assertThat(dims.getMainAxis(), is(1.1));
        dims.setMainAxis(STRING);
        assertThat(dims.getMainAxis(), is(1.1));
    }

    @Test
    public void testGetSetMainAxisAngle() {
        dims.setMainAxisAngle("1.1");
        assertThat(dims.getMainAxisAngle(), is(1.1));
        dims.setMainAxisAngle(STRING);
        assertThat(dims.getMainAxisAngle(), is(1.1));
    }

    @Test
    public void testGetSetQualityCode() {
        dims.setQualityCode(STRING);
        assertThat(dims.getQualityCode(), is(STRING));
    }

    @Test
    public void testGetSetSmallAxis() {
        dims.setSmallAxis("1.1");
        assertThat(dims.getSmallAxis(), is(1.1));
        dims.setSmallAxis(STRING);
        assertThat(dims.getSmallAxis(), is(1.1));
    }

    @Test
    public void testGetSetWavelength() {
        dims.setWavelength(STRING);
        assertThat(dims.getWavelength(), is(STRING));
    }
}
