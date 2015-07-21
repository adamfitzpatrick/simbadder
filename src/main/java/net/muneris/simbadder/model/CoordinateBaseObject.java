package net.muneris.simbadder.model;

import org.apache.log4j.Logger;

public class CoordinateBaseObject {

	protected final Logger log;
	
	private double rightAscension;
	private double declination;
	private int precisionCode;
	private String qualityCode;
	private String errorEllipse;
	private String bibCode;
	
	public CoordinateBaseObject(Class<? extends CoordinateBaseObject> clazz) {
		log = Logger.getLogger(clazz);
	}
	
	public double getRightAscension() {
		return rightAscension;
	}
	public void setRightAscension(String rightAscension) {
		try {
			this.rightAscension = Double.parseDouble(rightAscension.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse right ascension value: " + rightAscension);
		}
	}
	
	public double getDeclination() {
		return declination;
	}
	
	public void setDeclination(String declination) {
		try {
			this.declination = Double.parseDouble(declination.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse declination: " + declination);
		}
	}
	
	public int getPrecisionCode() {
		return precisionCode;
	}
	
	public void setPrecisionCode(String precisionCode) {
		try {
			this.precisionCode = Integer.parseInt(precisionCode.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse precision code:" + precisionCode);
		}
	}
	
	public String getQualityCode() {
		return qualityCode;
	}
	
	public void setQualityCode(String qualityCode) {
		this.qualityCode = qualityCode.trim();
	}
	
	public String getErrorEllipse() {
		return errorEllipse;
	}
	
	public void setErrorEllipse(String errorEllipse) {
		this.errorEllipse = errorEllipse.trim();
	}
	
	public String getBibCode() {
		return bibCode;
	}
	
	public void setBibCode(String bibCode) {
		this.bibCode = bibCode.trim();
	}
}
