package net.muneris.simbadder.simbadapi.query;

import static net.muneris.simbadder.testUtils.TestConstants.*;
import static org.junit.Assert.*;
import net.muneris.simbadder.simbadapi.query.CustomQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomQueryTest {

	CustomQuery query;
	
	@Before
	public void setUp() throws Exception {
		query = new CustomQuery(STRING);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCustomQuery() {
		assertNotNull(query);
	}

	@Test
	public void testGetQueryString() {
		assertEquals(STRING, query.getQueryString());
	}

}
