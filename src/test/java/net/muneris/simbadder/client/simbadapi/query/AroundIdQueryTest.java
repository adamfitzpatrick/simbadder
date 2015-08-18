package net.muneris.simbadder.client.simbadapi.query;

import static net.muneris.simbadder.client.testUtils.TestConstants.*;
import static org.junit.Assert.*;
import net.muneris.simbadder.simbadapi.query.AroundIdQuery;
import net.muneris.simbadder.simbadapi.query.RadiusUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AroundIdQueryTest {

	AroundIdQuery query;
	
	@Before
	public void setUp() throws Exception {
		query = new AroundIdQuery(STRING, 1.0, RadiusUnit.DEGREES);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIdAroundQueryRadiusUnit() {
		assertNotNull(query);
	}
	
	@Test
	public void testIdAroundQueryRadiusUnitString(){
		query = new AroundIdQuery(STRING, 1.0, STRING2);
		assertNull(query.getUnit());
		query = new AroundIdQuery(STRING, 1.0, "d");
		assertEquals(query.getUnit(), RadiusUnit.DEGREES);
	}
	
	@Test
	public void testGetSetId() {
		query.setId(STRING2);
		assertEquals(STRING2, query.getId());
	}
	
	@Test
	public void testGetSetRadiu() {
		query.setRadius(0.5);
		assertEquals(0.5, query.getRadius(), 1e-10);
	}
	
	@Test
	public void testGetSetUnit() {
		query.setUnit(RadiusUnit.SECONDS);
		assertEquals(RadiusUnit.SECONDS, query.getUnit());
	}

	@Test
	public void testGetQueryString() {
		assertEquals(String.format("query around %s radius=%sd", STRING, 1.0),query.getQueryString());
	}

}
