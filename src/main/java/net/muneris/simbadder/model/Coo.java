package net.muneris.simbadder.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

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
