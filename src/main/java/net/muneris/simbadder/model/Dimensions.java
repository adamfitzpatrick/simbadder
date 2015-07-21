package net.muneris.simbadder.model;

import org.apache.log4j.Logger;

public class Dimensions {

	private static final Logger log = Logger.getLogger(Dimensions.class);
	
	private double mainAxis;
	private double smallAxis;
	private double mainAxisAngle;
	private int inclinationCode;
	private String wavelength;
	private String qualityCode;
	private String bibCode;
	
	public double getMainAxis() {
		return mainAxis;
	}
	
	public void setMainAxis(String mainAxis) {
		try {
			this.mainAxis = Double.parseDouble(mainAxis.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse mainAxis value: " + mainAxis);
		}
	}
	
	public double getSmallAxis() {
		return smallAxis;
	}
	
	public void setSmallAxis(String smallAxis) {
		try {
			this.smallAxis = Double.parseDouble(smallAxis.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse smallAxis value: " + smallAxis);
		}
	}
	
	public double getMainAxisAngle() {
		return mainAxisAngle;
	}
	
	public void setMainAxisAngle(String mainAxisAngle) {
		try {
			this.mainAxisAngle = Double.parseDouble(mainAxisAngle.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse mainAxisAngle value: " + mainAxisAngle);
		}
	}
	
	public int getInclinationCode() {
		return inclinationCode;
	}
	
	public void setInclinationCode(String inclinationCode) {
		try {
			this.inclinationCode = Integer.parseInt(inclinationCode.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse inclinationCode value: " + inclinationCode);
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
	
	public String getBibCode() {
		return bibCode;
	}
	
	public void setBibCode(String bibCode) {
		this.bibCode = bibCode.trim();
	}
}
