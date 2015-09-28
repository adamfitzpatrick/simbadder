package net.muneris.simbadder.model;

import static net.muneris.simbadder.testutils.TestConstants.STRING;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

public class FluxTest {

    Flux flux;
    
    @Before
    public void setUp() throws Exception {
        flux = new Flux();
    }

    @Test
    public void testSetGetBibCode() {
        flux.setBibCode(STRING);
        assertThat(flux.getBibCode(), is(STRING));
    }

    @Test
    public void testSetGetError() {
        flux.setError("1.1");
        assertThat(flux.getError(), is(1.1));
        flux.setError(STRING);
        assertThat(flux.getError(), is(1.1));
    }

    @Test
    public void testSetGetMultiplicity() {
        flux.setMultiplicity(STRING);
        assertThat(flux.getMultiplicity(), is(STRING));
    }

    @Test
    public void testSetGetName() {
        flux.setName(STRING);
        assertThat(flux.getName(), is(STRING));
    }

    @Test
    public void testSetGetQuality() {
        flux.setQuality(STRING);
        assertThat(flux.getQuality(), is(STRING));
    }

    @Test
    public void testSetGetUnit() {
        flux.setUnit(STRING);
        assertThat(flux.getUnit(), is(STRING));
    }

    @Test
    public void testSetGetValue() {
        flux.setValue("1.1");
        assertThat(flux.getValue(), is(1.1));
        flux.setValue(STRING);
        assertThat(flux.getValue(), is(1.1));
    }

    @Test
    public void testSetGetVariability() {
        flux.setVariability(STRING);
        assertThat(flux.getVariability(), is(STRING));
    }

}
