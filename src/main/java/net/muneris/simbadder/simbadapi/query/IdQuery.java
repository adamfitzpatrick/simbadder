package net.muneris.simbadder.simbadapi.query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates properly formatted identifier queries for submission to SIMBAD.
 * Identifier querys are directed to the script query URL at simbad, and take
 * the following form:
 * <pre>
 *     query id <identifier 1>
 *              <identifier 2>
 *              ...
 * </pre>
 * @author adam.fitzpatrick
 *
 */
public class IdQuery implements Query {

	private List<String> identifiers;
	
	public IdQuery() {
		this.identifiers = new ArrayList<>();
	}
	
	public IdQuery(String identifier) {
		this();
		this.identifiers.add(identifier);
	}
	
	public IdQuery(List<String> identifiers) {
		this();
		this.identifiers = identifiers;
	}
	
	public List<String> getIdentifiers() {
		return this.identifiers;
	}
	
	public void setIdentifiers(List<String> identifiers) {
		this.identifiers = identifiers;
	}
	
	public void addIdentifier(String identifier) {
		this.identifiers.add(identifier);
	}
	
	@Override
	public String getQueryString() {
		return "query id " + identifiers.stream().collect(Collectors.joining("\n"));
	}
	
	

}
