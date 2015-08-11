package net.muneris.simbadder.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Data model for the complete COO data set provided by SIMBAD.
 * Note that much of the members of the COO data are duplicated in other
 * SIMBAD data sets, so this class extends the {@link CoordinateBaseObject}
 * and only provides wavelengthType information to complete the set.
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class Coo extends CoordinateBaseObject{

	private String wavelengthType;
	
	public Coo() {
		super(Coo.class);
	}
	
	@JsonGetter("wavelengthCode")
	public String getWavelengthType() {
		return wavelengthType;
	}
	
	@JsonSetter("wavelength")
	public void setWavelengthType(String wavelengthType) {
		this.wavelengthType = wavelengthType.trim();
	}
}
