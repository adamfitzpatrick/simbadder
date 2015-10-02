package net.muneris.simbadder.simbadapi.query;

/**
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class CooQuery implements Query {

    private double ra;
    private double dec;
    private double radius;
    private RadiusUnit unit;
    private String epoch;
    private String equi;
    
    public CooQuery(double ra, double dec, double radius,
            RadiusUnit unit, String epoch, String equi) {
        this.ra = ra;
        this.dec = dec;
        this.radius = radius;
        this.unit = unit;
        this.epoch = epoch;
        this.equi = equi;
    }
    
    @Override
    public String getQueryString() {
        return String.format("query coo %s %s %s%s%s",
                ra, dec, getRadiusStr(), getEpochStr(), getEquiStr());
    }

    public double getRa() {
        return ra;
    }

    public void setRa(double ra) {
        this.ra = ra;
    }

    public double getDec() {
        return dec;
    }

    public void setDec(double dec) {
        this.dec = dec;
    }
    
    public double getRadius() {
        return radius;
    }
    
    public void setRadius(double radius) {
        this.radius = radius;
    }

    public RadiusUnit getUnit() {
        return unit;
    }

    public void setUnit(RadiusUnit unit) {
        this.unit = unit;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    public String getEqui() {
        return equi;
    }

    public void setEqui(String equi) {
        this.equi = equi;
    }
    
    private String getRadiusStr() {
        return String.format("radius=%s%s", radius, unit.toString());
    }
    
    private String getEpochStr() {
        if (epoch != null) {
            return String.format(" epoch=%s", epoch);
        }
        return "";
    }
    
    private String getEquiStr() {
        if (equi != null) {
            return String.format(" equi=%s", equi);
        }
        return "";
    }
}
