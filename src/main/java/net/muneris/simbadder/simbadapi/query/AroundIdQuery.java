package net.muneris.simbadder.simbadapi.query;

public class AroundIdQuery implements Query {

	private String id;
	private double radius;
	private RadiusUnit unit;
	
	public AroundIdQuery(String id, double radius, RadiusUnit unit) {
		this.id = id;
		this.radius = radius;
		this.unit = unit;
	}
	
	public AroundIdQuery(String id, double radius, String unitStr) {
		this(id, radius, RadiusUnit.parseString(unitStr));
	}
	
	@Override
	public String getQueryString() {
		return String.format("query around %s radius=%s%s", id, radius, unit);
	}

}
