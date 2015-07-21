package net.muneris.simbadder.model;

import org.jboss.logging.Logger;

public class BibCode {

	private static final Logger log = Logger.getLogger(BibCode.class);
	private String bibCode;
	private String coordinates;
	private String title;
	private int lastPage;
	private String authors;
	private String comments;
	private String errata;
	private String nomenclatureDictionary;
	private String flags;
	private String files;
	private String notes;
	private String status;
	
	public String getBibCode() {
		return bibCode;
	}
	
	public void setBibCode(String bibCode) {
		this.bibCode = bibCode.trim();
	}
	
	public String getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates.trim();
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title.trim();
	}
	
	public int getLastPage() {
		return lastPage;
	}
	
	public void setLastPage(String lastPage) {
		try {
			this.lastPage = Integer.parseInt(lastPage);
		} catch (NumberFormatException e) {
			log.debug("Unable to parse lastPage: " + lastPage);
		}
	}
	
	public String getAuthors() {
		return authors;
	}
	
	public void setAuthors(String authors) {
		this.authors = authors.trim();
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments.trim();
	}
	
	public String getErrata() {
		return errata;
	}
	
	public void setErrata(String errata) {
		this.errata = errata.trim();
	}
	
	public String getNomenclatureDictionary() {
		return nomenclatureDictionary;
	}
	
	public void setNomenclatureDictionary(String nomenclatureDictionary) {
		this.nomenclatureDictionary = nomenclatureDictionary.trim();
	}
	
	public String getFlags() {
		return flags;
	}
	
	public void setFlags(String flags) {
		this.flags = flags.trim();
	}
	
	public String getFiles() {
		return files;
	}
	
	public void setFiles(String files) {
		this.files = files.trim();
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes.trim();
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status.trim();
	}
}
