package net.muneris.simbadder.model;

import org.apache.log4j.Logger;

/**
 * Provides a base class for coordinate data collections.  As this type of
 * information is found in several different data fields provided by SIMBAD,
 * this class is extended by other classes to provide a complete data set.
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class CoordinateBaseObject {

	protected final Logger log;
	
	private double rightAscension;
	private double declination;
	private int precisionCode;
	private String qualityCode;
	private String errorEllipse;
	private String bibCode;
	
	/** Sole constructor, should be called by all class that extend this one.*/
	public CoordinateBaseObject(Class<? extends CoordinateBaseObject> clazz) {
		log = Logger.getLogger(clazz);
	}
	
	/**
	 * Right ascension describes the location of the object in the night sky as
	 * an analogue to global longitude.  It is measured relative to the direction
	 * of the vernal equinox, and is often described in units of h:mm:ss.  For
	 * the sake of simplicity and precision, at this time rightAscension is only 
	 * available in decimal values.
	 * @return Right ascension in decimal units in the range [0,24) hours
	 */
	public double getRightAscension() {
		return rightAscension;
	}
	
	/**
	 * Right ascension describes the location of the object in the night sky as
	 * an analog to global longitude.  It is measured relative to the direction
	 * of the vernal equinox, and is often described in units of h:mm:ss.  For
	 * the sake of simplicity and precision, at this time rightAscension setters
	 * only accept decimal values
	 * @param rightAscension String representation of double in range [0,24) hours
	 */
	public void setRightAscension(String rightAscension) {
		try {
			this.rightAscension = Double.parseDouble(rightAscension.trim());
		} catch (NumberFormatException e) {
			log.debug("Unable to parse right ascension value: " + rightAscension);
		}
	}
	/**
	 * Declination describes the location of the object in the night sky as an
	 * analog to global latitude.  It is measured in degrees north and south of
	 * the celestial equator, which occupies the same plane as the earth's equator.
	 * Although declination is sometimes described in deg:mm:ss, at this time
	 * the value is only available as a double.
	 * @return Declination in the range [-90, 90] degrees
	 */
	public double getDeclination() {
		return declination;
	}
	
	/**
	 * Declination describes the location of the object in the night sky as an
	 * analog to global latitude.  It is measured in degrees north and south of
	 * the celestial equator, which occupies the same plane as the earth's equator.
	 * Although declination is sometimes described in deg:mm:ss, at this time
	 * the value can only be set with a decimal representation.
	 * @param declination String representation of double in range [-90,90] degrees
	 */
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
