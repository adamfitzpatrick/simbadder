package net.muneris.simbadder.model;

import org.apache.log4j.Logger;

/**
 * Data model for object dimensions from SIMBAD.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class Dimensions {

    private static final Logger LOGGER = Logger.getLogger(Dimensions.class);

    private double mainAxis;
    private double smallAxis;
    private double mainAxisAngle;
    private int inclinationCode;
    private String wavelength;
    private String qualityCode;
    private String bibCode;

    public String getBibCode() {
        return bibCode;
    }

    public int getInclinationCode() {
        return inclinationCode;
    }

    public double getMainAxis() {
        return mainAxis;
    }

    public double getMainAxisAngle() {
        return mainAxisAngle;
    }

    public String getQualityCode() {
        return qualityCode;
    }

    public double getSmallAxis() {
        return smallAxis;
    }

    public String getWavelength() {
        return wavelength;
    }

    public void setBibCode(String bibCode) {
        this.bibCode = bibCode.trim();
    }

    public void setInclinationCode(String inclinationCode) {
        try {
            this.inclinationCode = Integer.parseInt(inclinationCode.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse inclinationCode value: " + inclinationCode);
        }
    }

    public void setMainAxis(String mainAxis) {
        try {
            this.mainAxis = Double.parseDouble(mainAxis.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse mainAxis value: " + mainAxis);
        }
    }

    public void setMainAxisAngle(String mainAxisAngle) {
        try {
            this.mainAxisAngle = Double.parseDouble(mainAxisAngle.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse mainAxisAngle value: " + mainAxisAngle);
        }
    }

    public void setQualityCode(String qualityCode) {
        this.qualityCode = qualityCode.trim();
    }

    public void setSmallAxis(String smallAxis) {
        try {
            this.smallAxis = Double.parseDouble(smallAxis.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse smallAxis value: " + smallAxis);
        }
    }

    public void setWavelength(String wavelength) {
        this.wavelength = wavelength.trim();
    }
}
