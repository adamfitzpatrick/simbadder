package net.muneris.simbadder.model;

public class Type {

	private String value;
	private String quality;
	private String bibCode;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value.trim();
	}
	
	public String getQuality() {
		return quality;
	}
	
	public void setQuality(String quality) {
		this.quality = quality.trim();
	}
	
	public String getBibCode() {
		return bibCode;
	}
	
	public void setBibCode(String bibCode) {
		this.bibCode = bibCode.trim();
	}
}
