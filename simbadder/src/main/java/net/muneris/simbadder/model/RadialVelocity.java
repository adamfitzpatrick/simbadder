package net.muneris.simbadder.model;

import org.apache.log4j.Logger;

/**
 * Data model for accepting information on object radial velocity from SIMBAD.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class RadialVelocity {

    private static final Logger LOGGER = Logger.getLogger(RadialVelocity.class);

    private String type;
    private double valueStored;
    private double radialVelocityValue;
    private double redshiftValue;
    private double czValue;
    private String wavelength;
    private String qualityCode;
    private double error;
    private String bibCode;

    public String getBibCode() {
        return bibCode;
    }

    public double getCzValue() {
        return czValue;
    }

    public double getError() {
        return error;
    }

    public String getQualityCode() {
        return qualityCode;
    }

    public double getRadialVelocityValue() {
        return radialVelocityValue;
    }

    public double getRedshiftValue() {
        return redshiftValue;
    }

    public String getType() {
        return type;
    }

    public double getValueStored() {
        return valueStored;
    }

    public String getWavelength() {
        return wavelength;
    }

    public void setBibCode(String bibCode) {
        this.bibCode = bibCode.trim();
    }

    public void setCzValue(String czValue) {
        try {
            this.czValue = Double.parseDouble(czValue.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse czValue: " + czValue);
        }
    }

    public void setError(String error) {
        try {
            this.error = Double.parseDouble(error.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse error value: " + error);
        }
    }

    public void setQualityCode(String qualityCode) {
        this.qualityCode = qualityCode.trim();
    }

    public void setRadialVelocityValue(String radialVelocityValue) {
        try {
            this.radialVelocityValue = Double.parseDouble(radialVelocityValue.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse radialVelocityValue: " + radialVelocityValue);
        }
    }

    public void setRedshiftValue(String redshiftValue) {
        try {
            this.redshiftValue = Double.parseDouble(redshiftValue.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse redshiftValue: " + redshiftValue);
        }
    }

    public void setType(String type) {
        this.type = type.trim();
    }

    public void setValueStored(String valueStored) {
        try {
            this.valueStored = Double.parseDouble(valueStored.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse valueStored value: " + valueStored);
        }
    }

    public void setWavelength(String wavelength) {
        this.wavelength = wavelength.trim();
    }
}
