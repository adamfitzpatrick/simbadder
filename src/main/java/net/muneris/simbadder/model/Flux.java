package net.muneris.simbadder.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.apache.log4j.Logger;

/**
 * Data model for accepting flux information from SIMBAD.
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class Flux {

    private static final Logger LOGGER = Logger.getLogger(Flux.class);

    private String name;
    private String unit;
    private double value;
    private double error;
    private String quality;
    private String multiplicity;
    private String variability;
    private String bibCode;

    @JsonGetter("bibCode")
    public String getBibCode() {
        return bibCode;
    }

    @JsonGetter("error")
    public double getError() {
        return error;
    }

    @JsonGetter("multiplicity")
    public String getMultiplicity() {
        return multiplicity;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("quality")
    public String getQuality() {
        return quality;
    }

    @JsonGetter("unit")
    public String getUnit() {
        return unit;
    }

    @JsonGetter("value")
    public double getValue() {
        return value;
    }

    @JsonGetter("variability")
    public String getVariability() {
        return variability;
    }

    @JsonSetter("z8")
    public void setBibCode(String bibCode) {
        this.bibCode = bibCode.trim();
    }

    @JsonSetter("z4")
    public void setError(String error) {
        try {
            this.error = Double.parseDouble(error.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse error value: " + error);
        }
    }

    @JsonSetter("z6")
    public void setMultiplicity(String multiplicity) {
        this.multiplicity = multiplicity.trim();
    }

    @JsonSetter("z1")
    public void setName(String name) {
        this.name = name.trim();
    }

    @JsonSetter("z5")
    public void setQuality(String quality) {
        this.quality = quality.trim();
    }

    @JsonSetter("z2")
    public void setUnit(String unit) {
        this.unit = unit.trim();
    }

    @JsonSetter("z3")
    public void setValue(String value) {
        try {
            this.value = Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse value: " + value);
        }
    }

    @JsonSetter("z7")
    public void setVariability(String variability) {
        this.variability = variability.trim();
    }
}
