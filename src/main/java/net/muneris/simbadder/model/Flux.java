package net.muneris.simbadder.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import org.apache.log4j.Logger;

public class Flux {
	
	private static final Logger log = Logger.getLogger(Flux.class);
	
	private String name;
	private String unit;
	private double value;
	private double error;
	private String quality;
	private String multiplicity;
	private String variability;
	private String bibCode;
	
	@JsonGetter("name")
	public String getName() {
		return name;
	}
	
	@JsonSetter("z1")
	public void setName(String name) {
		this.name = name.trim();
	}

	@JsonGetter("unit")
	public String getUnit() {
		return unit;
	}
	
	@JsonSetter("z2")
	public void setUnit(String unit) {
		this.unit = unit.trim();
	}

	@JsonGetter("value")
	public double getValue() {
		return value;
	}
	
	@JsonSetter("z3")
	public void setValue(String value) {
		try {
			this.value = Double.parseDouble(value.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse value: " + value);
		}
	}

	@JsonGetter("error")
	public double getError() {
		return error;
	}
	
	@JsonSetter("z4")
	public void setError(String error) {
		try {
			this.error = Double.parseDouble(error.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse error value: " + error);
		}
	}
	
	@JsonGetter("quality")
	public String getQuality() {
		return quality;
	}
	
	@JsonSetter("z5")
	public void setQuality(String quality) {
		this.quality = quality.trim();
	}

	@JsonGetter("multiplicity")
	public String getMultiplicity() {
		return multiplicity;
	}
	
	@JsonSetter("z6")
	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity.trim();
	}

	@JsonGetter("variability")
	public String getVariability() {
		return variability;
	}
	
	@JsonSetter("z7")
	public void setVariability(String variability) {
		this.variability = variability.trim();
	}

	@JsonGetter("bibCode")
	public String getBibCode() {
		return bibCode;
	}
	
	@JsonSetter("z8")
	public void setBibCode(String bibCode) {
		this.bibCode = bibCode.trim();
	}
}
