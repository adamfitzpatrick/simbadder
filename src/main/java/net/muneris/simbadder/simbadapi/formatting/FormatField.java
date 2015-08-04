package net.muneris.simbadder.simbadapi.formatting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FormatField allows for simple and worry-free definition of field formatting
 * from SIMBAD.  Using this enumeration provides the ability specify a limited
 * selection of top-level data items.
 * 
 * Although it is not possible to specify
 * a more granular level of formatting options with this object, each top level
 * formatting element will incorporate all data that is available for that set.
 * 
 * For example, specifying the coordinate (COO) format item means that the data
 * returned in the COO object will also contain right ascension (ra), declination
 * (dec), wavelength, quality code, error ellipse, and bibcode.
 * 
 * Note that some of the formatting strings do not work quite as advertised by
 * SIMBAD.  For instance, %OTYPELIST allows the inclusion of any text in the
 * options string which is not a specific, case-sensitive options specifier.
 * Other formatting codes, however, are not case sensitive, so attempting to
 * include words in the string may have unpredictable results.  As such, for JSON
 * parsing it was necessary at times to use property names designed around avoiding
 * option specifiers.  Refer especially to FLUXLIST.
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 *
 */
public enum FormatField {

	MAINOTYPE("mainOType: { %OTYPE(numeric: !`!N!`!, shortname: !`!S!`!, veryshortname: !`!3!`!, "
			+ "verbose: !`!V!`!) }"),
	OTYPELIST("oTypeList: [%OTYPELIST({ numeric: !`!N!`!, shortname: !`!S!`!, veryshortname: "
			+ "!`!3!`!, verbose: !`!V!`! })!`! }]"),
	COO("coo: { rightAscension: !`!%COO(d;A;;;)!`!, declination: !`!%COO(d;D;;;)!`!, "
			+ "precisionCode: !`!%COO(;P;;;)!`!, wavelength: !`!%COO(;W;;;)!`!, "
			+ "qualityCode: !`!%COO(;Q;;;)!`!, errorEllipse: !`!%COO(;E;;;)!`!, "
			+ "bibCode: !`!%COO(;B;;;)!`! }"),
	DIST("distance: !`!%DIST!`!"),
	PM("properMotion: { rightAscension: !`!%PM(A)!`!, declination: !`!%PM(D)!`!, "
			+ "precisionCode: !`!%PM(P)!`!, qualityCode: !`!%PM(Q)!`!, "
			+ "errorEllipse: !`!%PM(E)!`!, bibCode: !`!%PM(B)!`! }"),
	PLX("parallax: { value: !`!%PLX(V)!`!, qualityCode: !`!%PLX(Q)!`!, error: !`!%PLX(E)!`!, "
			+ "bibCode: !`!%PLX(B)!`! }"),
	RV("radialVelocity: { type: !`!%RV(T)!`!, valueStored: !`!%RV(R)!`!, "
			+ "radialVelocityValue: !`!%RV(V)!`!, redshiftValue: !`!%RV(Z)!`!, "
			+ "czValue: !`!%RV(C)!`!, wavelength: !`!%RV(W)!`!, qualityCode: !`!%RV(Q)!`!, "
			+ "error: !`!%RV(E)!`!, bibCode: !`!%RV(B)!`! }"),
	FLUXLIST("fluxList: [%FLUXLIST[%*({ z1: !`!N!`!, z2: !`!U!`!, z3: !`!F!`!, "
			+ "z4: !`!E!`!, z5: !`!Q!`!, z6: !`!M!`!, z7: !`!V!`!, "
			+ "z8: !`!B!`! })]]"),
	SP("spectralType: { value: !`!%SP(s)!`!, quality: !`!%SP(Q)!`!, bibCode: !`!%SP(B)!`! }"),
	MT("morphologicalType: { value: !`!%SP(s)!`!, quality: !`!%SP(Q)!`!, bibCode: !`!%SP(B)!`! }"),
	DIM("dimensions: {mainAxis: !`!%DIM(X)!`!, smallAxis: !`!%DIM(Y)!`!, "
			+ "mainAxisAngle: !`!%DIM(A)!`!, inclinationCode: !`!%DIM(I)!`!, "
			+ "wavelength: !`!%DIM(W)!`!, qualityCode: !`!%DIM(Q)!`!, bibCode: !`!%DIM(B)!`! }"),
	MAINID("mainId: !`!%MAIN_ID!`!"),
	IDLIST("idList: !`!%IDLIST[%*(S)|]!`!"),
	BIBCODELIST("bibCodeList: [%BIBCODELIST(R;;;;{ bibCode: !`!%B!`!, coordinates: !`!%J!`!, "
			+ "title: !`!%T!`!, lastPage: !`!%L!`!, authors: !`!%A!`!, comments: !`!%P!`!, "
			+ "errata: !`!%E!`!, nomenclatureDictionary: !`!%D!`!, flags: !`!I!`!, "
			+ "files: !`!%F!`!, notes: !`!%N!`!, status: !`!%S!`! })]"),
	MEASLIST("measurements: !`!%MEASLIST[%*(CHA)\\n\\n]!`!"),
	NOTES("notes: !`!%NOTELIST!`!");
	
	
	private String fieldString;
	
	/**
	 * Convenience method to return all possible formats as a list.
	 * @return list of all possible FormatField constants.
	 */
	public static List<FormatField> getAll() {
		return Arrays.asList(FormatField.class.getEnumConstants());
	}
	
	/**
	 * Convenience method to return all formats except FormatField.DIST.
	 * FormatField.DIST is only applicable when performing a search around
	 * coordinates or an object ID.  Requesting that information from a
	 * search that cannot provide it provokes an error response from SIMBAD.
	 * @return list of formats excluding FormatField.DIST
	 */
	public static List<FormatField> getAllNotDist() {
		return getAll().stream().filter(p -> !p.equals(FormatField.DIST)).collect(Collectors.toList());
	}
	
	private FormatField(String fieldString) {
		this.fieldString = fieldString;
	}
	
	/**
	 * Returns the formatting string for this object that will be passed to SIMBAD
	 * @return specifier for object and all object member data
	 */
	public String valueOf() {
		return fieldString;
	}
}

