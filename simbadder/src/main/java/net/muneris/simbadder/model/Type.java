package net.muneris.simbadder.model;

/**
 * Data object for accepting spectral and morphological type information from
 * SIMBAD.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public class Type {

    private String value;
    private String quality;
    private String bibCode;

    public String getBibCode() {
        return bibCode;
    }

    public String getQuality() {
        return quality;
    }

    public String getValue() {
        return value;
    }

    public void setBibCode(String bibCode) {
        this.bibCode = bibCode.trim();
    }

    public void setQuality(String quality) {
        this.quality = quality.trim();
    }

    public void setValue(String value) {
        this.value = value.trim();
    }
}
