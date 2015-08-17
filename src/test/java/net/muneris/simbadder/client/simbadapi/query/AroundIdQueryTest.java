package net.muneris.simbadder.client.simbadapi.query;

import static net.muneris.simbadder.client.testUtils.TestConstants.*;
import static org.junit.Assert.*;
import net.muneris.simbadder.simbadapi.query.AroundIdQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AroundIdQueryTest {

	AroundIdQuery query;
	
	@Before
	public void setUp() throws Exception {
		query = new AroundIdQuery(STRING, 1.0, STRING2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIdAroundQuery() {
		assertNotNull(query);
	}

	@Test
	public void testGetQueryString() {
		assertEquals(String.format("query around %s radius=%s%s", STRING, 1.0, STRING2),query.getQueryString());
	}

}
