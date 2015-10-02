package net.muneris.simbadder.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import net.muneris.simbadder.simbadapi.query.RadiusUnit;

import org.junit.Test;

public class CoordinateValidatorTest {

    CoordinateValidator validator = new CoordinateValidator();

    @Test
    public void testValidLatitude() {
        assertThat(validator.validLatitude(45.0), is(true));
        assertThat(validator.validLatitude(-45.0), is(true));
        assertThat(validator.validLatitude(95.0), is(false));
        assertThat(validator.validLatitude(-95.0), is(false));
    }

    @Test
    public void testValidLongitude() {
        assertThat(validator.validLongitude(185.0), is(true));
        assertThat(validator.validLongitude(-175.0), is(true));
        assertThat(validator.validLongitude(365.0), is(false));
        assertThat(validator.validLongitude(-185.0), is(false));
    }

    @Test
    public void testValidRadius() {
        assertThat(validator.validRadius(-10.0, RadiusUnit.DEGREES), is(false));
        assertThat(validator.validRadius(180.0, RadiusUnit.DEGREES), is(true));
        assertThat(validator.validRadius(185.0, RadiusUnit.DEGREES), is(false));

        assertThat(validator.validRadius(10700.0, RadiusUnit.MINUTES), is(true));
        assertThat(validator.validRadius(10850.0, RadiusUnit.MINUTES), is(false));

        assertThat(validator.validRadius(647000.0, RadiusUnit.SECONDS), is(true));
        assertThat(validator.validRadius(648500.0, RadiusUnit.SECONDS), is(false));
        
    }

}
