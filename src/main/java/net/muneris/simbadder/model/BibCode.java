package net.muneris.simbadder.model;

import org.jboss.logging.Logger;

/**
 * <p>Provides a data model for bibCodes (bibliography codes) provided
 * by SIMBAD.  Please refer to the University of Strasbourg's website for more
 * information on bibCodes and how they are used.</p>
 * 
 * <p>See <a href="http://simbad.u-strasbg.fr/guide/sim-ref.htx">http://simbad.u-strasbg.fr/guide/sim-ref.htx</a><br>
 * See <a href="http://simbad.u-strasbg.fr/guide/refcode/refcode-paper.html">
 * http://simbad.u-strasbg.fr/guide/refcode/refcode-paper.html</a></p>
 * @author adam.fitzpatrick
 */
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
	
	/**
	 * Accessor for the bibCode string.
	 * @return 19-digit bibCode
	 */
	public String getBibCode() {
		return bibCode;
	}
	
	/**
	 * Setter for the bibCode string.
	 * @param bibCode 19-digit code provided by SIMBAD
	 */
	public void setBibCode(String bibCode) {
		this.bibCode = bibCode.trim();
	}
	
	/**
	 * Accessor for reference citation/coordinates.
	 * @return Journal source, pages and publication year as a string
	 */
	public String getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Setter for reference coordinates.
	 * @param coordinates string provided by SIMBAD
	 */
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates.trim();
	}
	
	/**
	 * Accessor for article title.
	 * @return title string
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter for article title.
	 * @param title string provided by SIMBAD
	 */
	public void setTitle(String title) {
		this.title = title.trim();
	}
	
	/**
	 * Accessor for last page of article as it appears in original publication.
	 * @return integer page number
	 */
	public int getLastPage() {
		return lastPage;
	}
	
	/**
	 * Setter for article last page as it appears in original publication.
	 * @param lastPage last page as string provided by SIMBAD
	 */
	public void setLastPage(String lastPage) {
		try {
			this.lastPage = Integer.parseInt(lastPage);
		} catch (NumberFormatException e) {
			log.debug("Unable to parse lastPage: " + lastPage);
		}
	}
	
	/**
	 * Accessor for string listing of article authors.
	 * @return string of author names
	 */
	public String getAuthors() {
		// TODO Provide authors as List<String> of names
		return authors;
	}
	
	/**
	 * Setter for string listing of article authors.
	 * @param authors string provided by SIMBAD
	 */
	public void setAuthors(String authors) {
		// TODO Parse authors string from SIMBAD into List<String> of names
		this.authors = authors.trim();
	}
	
	/**
	 * Returns any comments regarding publication status or other information.
	 * @return comments
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * Setter for publication comments.
	 * @param comments string provided by SIMBAD
	 */
	public void setComments(String comments) {
		this.comments = comments.trim();
	}
	
	/**
	 * Accessor for errata listings.
	 * @return string of errata volumes
	 */
	public String getErrata() {
		return errata;
	}
	
	/**
	 * Setter for errata listings.
	 * @param errata string provided by SIMBAD
	 */
	public void setErrata(String errata) {
		this.errata = errata.trim();
	}
	
	/**
	 * Accessor for nomenclature dictionary.
	 * @return string representation of dictionary, if available
	 */
	public String getNomenclatureDictionary() {
		return nomenclatureDictionary;
	}
	
	/**
	 * Setter for nomenclature dictionary.
	 * @param nomenclatureDictionary string provided by SIMBAD
	 */
	public void setNomenclatureDictionary(String nomenclatureDictionary) {
		this.nomenclatureDictionary = nomenclatureDictionary.trim();
	}
	
	/**
	 * Accessor for publication flags.
	 * @return string listing of flags
	 */
	public String getFlags() {
		return flags;
	}
	
	/**
	 * Setter for publication flags.
	 * @param flags string provided by SIMBAD
	 */
	public void setFlags(String flags) {
		this.flags = flags.trim();
	}
	
	/**
	 * Provides a list files available at Centre de Donnees, if available
	 * @return string of file data
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
	 * Provides a list of notes related to the publication
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
	 * Returns information regarding publication status, such as digitization process.
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
