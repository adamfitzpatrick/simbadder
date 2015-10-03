package net.muneris.simbadder.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import org.jboss.logging.Logger;
import org.springframework.hateoas.ResourceSupport;

/**
 * Data model to contain all object information that is offered by SIMBAD. Refer
 * to constituent objects for more information.
 *
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimbadObject extends ResourceSupport {

    private static final Logger LOGGER = Logger.getLogger(SimbadObject.class);

    private OType mainOType;
    private List<OType> oTypeList;
    private Coo coo;
    private double distance;
    private ProperMotion pm;
    private Parallax parallax;
    private RadialVelocity radialVelocity;
    private List<Flux> fluxList;
    private Type spectralType;
    private Type morphologicalType;
    private Dimensions dimensions;
    private String mainId;
    private List<Identifier> idList;
    private List<BibCode> bibCodeList;
    private String measurements;
    private String notes;

    public List<BibCode> getBibCodeList() {
        return bibCodeList;
    }

    public Coo getCoo() {
        return coo;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public double getDistance() {
        return distance;
    }

    public List<Flux> getFluxList() {
        return fluxList;
    }

    public List<Identifier> getIdList() {
        return idList;
    }

    public String getMainId() {
        return mainId;
    }

    public OType getMainOType() {
        return mainOType;
    }

    /**
     * Measurements data provided by SIMBAD is formatted as an ascii table using
     * pipe characters to separate columns, but some columns consist of data
     * pairs and attempts to format data under the expectation of monospaced
     * fonts, resulting in information that is difficult to parse for a general
     * use-case. As a result, this service simply forwards the data from SIMBAD
     * directly as a String. Extracting desired information from the String is
     * left to the end-user and their individual needs.
     *
     * @return a string of measurement data from SIMBAD.
     * @see http://simbad.u-strasbg.fr/simbad/sim-help?Page=sim-fscript#Formats
     */
    public String getMeasurements() {
        return measurements;
    }

    public Type getMorphologicalType() {
        return morphologicalType;
    }

    public String getNotes() {
        return notes;
    }

    @JsonGetter("oTypeList")
    public List<OType> getOTypeList() {
        return oTypeList;
    }

    public Parallax getParallax() {
        return parallax;
    }

    public ProperMotion getProperMotion() {
        return pm;
    }

    public RadialVelocity getRadialVelocity() {
        return radialVelocity;
    }

    public Type getSpectralType() {
        return spectralType;
    }

    public void setBibCodeList(List<BibCode> bibCodeList) {
        this.bibCodeList = bibCodeList;
    }

    public void setCoo(Coo coo) {
        this.coo = coo;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public void setDistance(String distance) {
        try {
            this.distance = Double.parseDouble(distance.trim());
        } catch (NumberFormatException e) {
            LOGGER.debug("Unable to parse distance: " + distance);
        }
    }

    public void setFluxList(List<Flux> fluxList) {
        this.fluxList = fluxList;
    }

    public void setIdList(String identifierString) {
        this.idList = Identifier.idListFactory(identifierString);

    }

    public void setMainId(String mainId) {
        this.mainId = mainId.trim();
    }

    public void setMainOType(OType mainOType) {
        this.mainOType = mainOType;
    }

    /**
     * Refer to comments for getMeasurements above.
     *
     * @param measurements
     *            string of measurement data from SIMBAD.
     */
    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }

    public void setMorphologicalType(Type morphologicalType) {
        this.morphologicalType = morphologicalType;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonSetter("oTypeList")
    public void setOTypeList(List<OType> oTypeList) {
        this.oTypeList = oTypeList;
    }

    public void setParallax(Parallax parallax) {
        this.parallax = parallax;
    }

    public void setProperMotion(ProperMotion pm) {
        this.pm = pm;
    }

    public void setRadialVelocity(RadialVelocity radialVelocity) {
        this.radialVelocity = radialVelocity;
    }

    public void setSpectralType(Type spectralType) {
        this.spectralType = spectralType;
    }
}
