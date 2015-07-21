package net.muneris.simbadder.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.jboss.logging.Logger;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SimbadObject {

	private static final Logger log = Logger.getLogger(SimbadObject.class);
	
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

	public OType getMainOType() {
		return mainOType;
	}

	public void setMainOType(OType mainOType) {
		this.mainOType = mainOType;
	}

	@JsonGetter("oTypeList")
	public List<OType> getOTypeList() {
		return oTypeList;
	}

	@JsonSetter("oTypeList")
	public void setOTypeList(List<OType> oTypeList) {
		this.oTypeList = oTypeList;
	}
	
	public Coo getCoo() {
		return coo;
	}
	
	public void setCoo(Coo coo) {
		this.coo = coo;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(String distance) {
		try {
			this.distance = Double.parseDouble(distance.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse distance: " + distance);
		}
	}
	
	public ProperMotion getProperMotion() {
		return pm;
	}
	
	public void setProperMotion(ProperMotion pm) {
		this.pm = pm;
	}
	
	public Parallax getParallax() {
		return parallax;
	}
	
	public void setParallax(Parallax parallax) {
		this.parallax = parallax;
	}
	
	public RadialVelocity getRadialVelocity() {
		return radialVelocity;
	}
	
	public void setRadialVelocity(RadialVelocity radialVelocity) {
		this.radialVelocity = radialVelocity;
	}
	
	public List<Flux> getFluxList() {
		return fluxList;
	}
	
	public void setFluxList(List<Flux> fluxList) {
		this.fluxList = fluxList;
	}
	
	public Type getSpectralType() {
		return spectralType;
	}
	
	public void setSpectralType(Type spectralType) {
		this.spectralType = spectralType;
	}
	
	public Type getMorphologicalType() {
		return morphologicalType;
	}
	
	public void setMorphologicalType(Type morphologicalType) {
		this.morphologicalType = morphologicalType;
	}
	
	public Dimensions getDimensions() {
		return dimensions;
	}
	
	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}
	
	public String getMainId() {
		return mainId;
	}
	
	public void setMainId(String mainId) {
		this.mainId = mainId.trim();
	}
	
	public List<Identifier> getIdList() {
		return idList;
	}
	
	public void setIdList(String identifierString) {
		this.idList = Identifier.idListFactory(identifierString);
		
	}
	
	public List<BibCode> getBibCodeList() {
		return bibCodeList;
	}
	
	public void setBibCodeList(List<BibCode> bibCodeList) {
		this.bibCodeList = bibCodeList;
	}
	
	/**
	 * Measurements data provided by SIMBAD is formatted as an ascii table
	 * using pipe characters to separate columns, but some columns consist
	 * of data pairs and attempts to format data under the expectation of
	 * monospaced fonts, resulting in information that is difficult to parse
	 * for a general use-case.  As a result, this service simply forwards the
	 * data from SIMBAD directly as a String.  Extracting desired information
	 * from the String is left to the end-user and their individual needs.
	 * @return a string of measurement data from SIMBAD.
	 * @see http://simbad.u-strasbg.fr/simbad/sim-help?Page=sim-fscript#Formats
	 */
	public String getMeasurements() {
		return measurements;
	}
	
	/**
	 * Refer to comments for getMeasurements above.
	 * @param string of measurement data from SIMBAD.
	 */
	public void setMeasurements(String measurements) {
		this.measurements = measurements;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
