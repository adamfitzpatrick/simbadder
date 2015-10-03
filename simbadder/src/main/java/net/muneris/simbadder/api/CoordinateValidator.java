package net.muneris.simbadder.api;

import net.muneris.simbadder.simbadapi.query.RadiusUnit;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */

@Component
public class CoordinateValidator {

    public boolean validLatitude(double latitude) {
        return latitude <= 90.0 && latitude >= -90;
    }
    
    public boolean validLongitude(double longitude) {
        return longitude < 360.0 && longitude > -180.0;
    }
    
    public boolean validRadius(double radius, RadiusUnit unit) {
        boolean nonNegative = radius >= 0;
        if (unit.equals(RadiusUnit.DEGREES)) {
            return nonNegative && radius <= 180.0;
        }
        if (unit.equals(RadiusUnit.MINUTES)) {
            return nonNegative && radius <= 180.0 * 60.0;
        }
        if (unit.equals(RadiusUnit.SECONDS)) {
            return nonNegative && radius <= 180.0 * 60.0 * 60.0;
        }
        return false;
    }
}
