package net.muneris.simbadder.model;

import org.apache.log4j.Logger;

/**
 * Data model for accepting parallax information from SIMBAD.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class Parallax {

    private static final Logger LOGGER = Logger.getLogger(Parallax.class);

    private double value;
    private String qualityCode;
    private String error;
    private String bibCode;

    public String getBibCode() {
        return bibCode;
    }

    public String getError() {
        return error;
    }

    public String getQualityCode() {
        return qualityCode;
    }

    public double getValue() {
        return value;
    }

    public void setBibCode(String bibCode) {
        this.bibCode = bibCode.trim();
    }

    public void setError(String error) {
        this.error = error.trim();
    }

    public void setQualityCode(String qualityCode) {
        this.qualityCode = qualityCode.trim();
    }

    public void setValue(String value) {
        try {
            this.value = Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse value: " + value);
        }
    }
}
