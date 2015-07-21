package net.muneris.simbadder.simbadapi.query;

public class CustomQuery implements Query {

	private final String queryString;
	
	public CustomQuery(String queryString) {
		this.queryString = queryString;
	}
	
	@Override
	public String getQueryString() {
		return queryString;
	}

}
