package net.muneris.simbadder.model;

import org.apache.log4j.Logger;

public class RadialVelocity {
	
	private static final Logger log = Logger.getLogger(RadialVelocity.class);
	
	private String type;
	private double valueStored;
	private double radialVelocityValue;
	private double redshiftValue;
	private double czValue;
	private String wavelength;
	private String qualityCode;
	private double error;
	private String bibCode;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type.trim();
	}
	
	public double getValueStored() {
		return valueStored;
	}
	
	public void setValueStored(String valueStored) {
		try {
			this.valueStored = Double.parseDouble(valueStored.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse valueStored value: " + valueStored);
		}
	}
	
	public double getRadialVelocityValue() {
		return radialVelocityValue;
	}
	
	public void setRadialVelocityValue(String radialVelocityValue) {
		try {
			this.radialVelocityValue = Double.parseDouble(radialVelocityValue.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse radialVelocityValue: " + radialVelocityValue);
		}
	}
	
	public double getRedshiftValue() {
		return redshiftValue;
	}
	
	public void setRedshiftValue(String redshiftValue) {
		try {
			this.redshiftValue = Double.parseDouble(redshiftValue.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse redshiftValue: " + redshiftValue);
		}
	}
	
	public double getCzValue() {
		return czValue;
	}
	
	public void setCzValue(String czValue) {
		try {
			this.czValue = Double.parseDouble(czValue.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse czValue: " + czValue);
		}
	}
	
	public String getWavelength() {
		return wavelength;
	}
	
	public void setWavelength(String wavelength) {
		this.wavelength = wavelength.trim();
	}
	
	public String getQualityCode() {
		return qualityCode;
	}
	
	public void setQualityCode(String qualityCode) {
		this.qualityCode = qualityCode.trim();
	}
	
	public double getError() {
		return error;
	}
	
	public void setError(String error) {
		try {
			this.error = Double.parseDouble(error.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse error value: " + error);
		}
	}
	
	public String getBibCode() {
		return bibCode;
	}
	
	public void setBibCode(String bibCode) {
		this.bibCode = bibCode.trim();
	}
}
