package net.muneris.simbadder.client.simbadapi.query;

import static net.muneris.simbadder.client.testUtils.TestConstants.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import net.muneris.simbadder.simbadapi.query.IdQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IdQueryTest {

	IdQuery idQuery;
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIdQuery() {
		assertNotNull(new IdQuery());
	}

	@Test
	public void testIdQueryString() {
		idQuery = new IdQuery(STRING);
		assertEquals(1, idQuery.getIdentifiers().size());
		assertEquals(STRING, idQuery.getIdentifiers().get(0));
	}

	@Test
	public void testIdQueryListOfString() {
		idQuery = new IdQuery(Arrays.asList(STRING, STRING2));
		assertEquals(2, idQuery.getIdentifiers().size());
		assertEquals(STRING, idQuery.getIdentifiers().get(0));
		assertEquals(STRING2, idQuery.getIdentifiers().get(1));
	}

	@Test
	public void testSetGetIdentifiers() {
		idQuery = new IdQuery();
		idQuery.setIdentifiers(Arrays.asList(STRING, STRING2));
		assertEquals(2, idQuery.getIdentifiers().size());
		assertEquals(STRING, idQuery.getIdentifiers().get(0));
		assertEquals(STRING2, idQuery.getIdentifiers().get(1));
	}

	@Test
	public void testAddIdentifier() {
		idQuery = new IdQuery();
		idQuery.addIdentifier(STRING);
		assertEquals(1, idQuery.getIdentifiers().size());
		assertEquals(STRING, idQuery.getIdentifiers().get(0));
	}

	@Test
	public void testGetQueryString() {
		idQuery = new IdQuery();
		idQuery.addIdentifier(STRING);
		assertEquals("query id " + STRING, idQuery.getQueryString());
	}

}
