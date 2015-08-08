package net.muneris.simbadder.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Identifier {

	private String catalog;
	private String id;
	
	public static List<Identifier> idListFactory(String identifierString) {
		List<Identifier> idList = new ArrayList<>();
		List<String> idStringList = Arrays.asList(identifierString.split("\\|"));
		idStringList.stream().forEach(identifier -> {
			String[] id = identifier.split(" ", 2);
			if (id.length < 2) {
				idList.add(new Identifier("", id[0]));
			} else {
				idList.add(new Identifier(id[0].trim(), id[1].trim()));
			}
		});
		return idList;
	}
	public Identifier(String catalog, String id) {
		this.catalog = catalog;
		this.id = id;
	}
	
	public String getCatalog() {
		return catalog;
	}
	
	public void setCatalog(String catalog) {
		this.catalog = catalog.trim();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id.trim();
	}
}