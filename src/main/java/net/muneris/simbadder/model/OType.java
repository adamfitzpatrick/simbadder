package net.muneris.simbadder.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Data model for acception information regarding object type from SIMBAD.
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OType {

    private String numeric;
    private String shortName;
    private String veryShortName;
    private String verbose;

    public String getNumeric() {
        return numeric;
    }

    @JsonGetter("shortName")
    public String getShortName() {
        return shortName;
    }

    public String getVerbose() {
        return verbose;
    }

    @JsonGetter("veryShortName")
    public String getVeryShortName() {
        return veryShortName;
    }

    public void setNumeric(String numeric) {
        this.numeric = numeric.trim();
    }

    @JsonSetter("shortname")
    public void setShortName(String shortName) {
        this.shortName = shortName.trim();
    }

    public void setVerbose(String verbose) {
        this.verbose = verbose.trim();
    }

    @JsonSetter("veryshortname")
    public void setVeryShortName(String veryShortName) {
        this.veryShortName = veryShortName.trim();
    }

}
