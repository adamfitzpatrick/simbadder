package net.muneris.simbadder.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OType {

	private String numeric;
	private String shortName;
	private String veryShortName;
	private String verbose;
	
	public String getNumeric() {
		return numeric;
	}
	
	public void setNumeric(String numeric) {
		this.numeric = numeric.trim();
	}
	
	@JsonGetter("shortName")
	public String getShortName() {
		return shortName;
	}
	
	@JsonSetter("shortname")
	public void setShortName(String shortName) {
		this.shortName = shortName.trim();
	}
	
	@JsonGetter("veryShortName")
	public String getVeryShortName() {
		return veryShortName;
	}
	
	@JsonSetter("veryshortname")
	public void setVeryShortName(String veryShortName) {
		this.veryShortName = veryShortName.trim();
	}
	
	public String getVerbose() {
		return verbose;
	}
	
	public void setVerbose(String verbose) {
		this.verbose = verbose.trim();
	}
	
}
