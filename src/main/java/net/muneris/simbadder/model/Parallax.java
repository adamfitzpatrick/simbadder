package net.muneris.simbadder.model;

import org.apache.log4j.Logger;

public class Parallax {

	private static final Logger log = Logger.getLogger(Parallax.class);
	
	private double value;
	private String qualityCode;
	private String error;
	private String bibCode;
	
	public double getValue() {
		return value;
	}
	
	public void setValue(String value) {
		try {
			this.value = Double.parseDouble(value.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse value: " + value);
		}
	}
	
	public String getQualityCode() {
		return qualityCode;
	}
	
	public void setQualityCode(String qualityCode) {
		this.qualityCode = qualityCode.trim();
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error.trim();
	}
	
	public String getBibCode() {
		return bibCode;
	}
	
	public void setBibCode(String bibCode) {
		this.bibCode = bibCode.trim();
	}
}
