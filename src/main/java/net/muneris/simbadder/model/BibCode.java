package net.muneris.simbadder.model;

import org.jboss.logging.Logger;

/**
 * This class provides a data model for bibCodes (bibliography codes) provided
 * by SIMBAD.  Please refer to the University of Strasbourg's website for more
 * information on bibCodes and how they are used.
 * @author adam.fitzpatrick
 * @see http://simbad.u-strasbg.fr/guide/sim-ref.htx
 * @see http://simbad.u-strasbg.fr/guide/refcode/refcode-paper.html
 */
public class BibCode {

	private static final Logger log = Logger.getLogger(BibCode.class);
	
	/** 19-digit code string allowing a publication to be located */
	private String bibCode;
	/** Citation string specifying journal, pages and publication year */
	private String coordinates;
	/** Article title */
	private String title;
	/** Last page of article as published */
	private int lastPage;
	/** String listing of authors */
	private String authors;
	/** Any comments regarding publication status or other information */
	private String comments;
	/** Errata listing, if applicable */
	private String errata;
	/** Nomenclature dictionary, if available */
	private String nomenclatureDictionary;
	/** Associated flags */
	private String flags;
	/** Files available at Centre de Données, if available */
	private String files;
	/** Additional details included in the reference */
	private String notes;
	/** Reference status, such as whether the reference is currently being processed */
	private String status;
	
	/**
	 * Accessor for the bibCode string
	 * @return 19-digit bibCode
	 */
	public String getBibCode() {
		return bibCode;
	}
	
	/**
	 * Setter for the bibCode string
	 * @param 19-digit code provided by SIMBAD
	 */
	public void setBibCode(String bibCode) {
		this.bibCode = bibCode.trim();
	}
	
	/**
	 * Accessor for reference coordinates.
	 * @return Journal source, pages and publication year as a string
	 */
	public String getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Setter for reference coordinates
	 * @param coordinate string provided by SIMBAD
	 */
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates.trim();
	}
	
	/**
	 * Accessor for article title
	 * @return title string
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter for article title
	 * @param title string provided by SIMBAD
	 */
	public void setTitle(String title) {
		this.title = title.trim();
	}
	
	/**
	 * Accessor for last page of article
	 * @return integer page number
	 */
	public int getLastPage() {
		return lastPage;
	}
	
	/**
	 * Setter for article last page
	 * @param last page as string provided by SIMBAD
	 */
	public void setLastPage(String lastPage) {
		try {
			this.lastPage = Integer.parseInt(lastPage);
		} catch (NumberFormatException e) {
			log.debug("Unable to parse lastPage: " + lastPage);
		}
	}
	
	/**
	 * TODO Provide authors as List<String> of names
	 * Accessor for string listing of article authors
	 * @return string of author names
	 */
	public String getAuthors() {
		return authors;
	}
	
	/**
	 * TODO Parse authors string from SIMBAD into List<String> of names
	 * Setter for string listing of article authors
	 * @param authors string provided by SIMBAD
	 */
	public void setAuthors(String authors) {
		this.authors = authors.trim();
	}
	
	/**
	 * Accessor for string listing of publication comments
	 * @return comments
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * Setter for publication comments
	 * @param comments string provided by SIMBAD
	 */
	public void setComments(String comments) {
		this.comments = comments.trim();
	}
	
	/**
	 * Accessor for errata listings
	 * @return string of errata volumes
	 */
	public String getErrata() {
		return errata;
	}
	
	/**
	 * Setter for errata listings
	 * @param errata string provided by SIMBAD
	 */
	public void setErrata(String errata) {
		this.errata = errata.trim();
	}
	
	/**
	 * Accessor for nomenclature dictionary
	 * @return string representation of dictionary, if available
	 */
	public String getNomenclatureDictionary() {
		return nomenclatureDictionary;
	}
	
	/**
	 * Setter for nomenclature dictionary
	 * @param dictionary string provided by SIMBAD
	 */
	public void setNomenclatureDictionary(String nomenclatureDictionary) {
		this.nomenclatureDictionary = nomenclatureDictionary.trim();
	}
	
	/**
	 * Accessor for publication flags
	 * @return string listing of flags
	 */
	public String getFlags() {
		return flags;
	}
	
	/**
	 * Setter for publication flags
	 * @param flags string provided by SIMBAD
	 */
	public void setFlags(String flags) {
		this.flags = flags.trim();
	}
	
	/**
	 * Accessor for files listing
	 * @return string of file data, if available
	 */
	public String getFiles() {
		return files;
	}
	
	/**
	 * Setter for files
	 * @param files string provided by SIMBAD
	 */
	public void setFiles(String files) {
		this.files = files.trim();
	}
	
	/**
	 * Accessor for notes related to the publication
	 * @return string of notes attached to reference.
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * Setter for publication notes
	 * @param notes string provided by SIMBAD
	 */
	public void setNotes(String notes) {
		this.notes = notes.trim();
	}
	
	/**
	 * Accessor for publication status
	 * @return string of status indicators
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Setter for publication status
	 * @param status string provided by SIMBAD.
	 */
	public void setStatus(String status) {
		this.status = status.trim();
	}
}
