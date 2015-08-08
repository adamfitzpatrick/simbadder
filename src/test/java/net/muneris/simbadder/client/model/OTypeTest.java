package net.muneris.simbadder.client.model;

import static org.junit.Assert.*;
import static net.muneris.simbadder.client.testUtils.TestConstants.*;
import net.muneris.simbadder.model.OType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OTypeTest {
	
	OType oType;
	
	@Before
	public void setUp() throws Exception {
		oType = new OType();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNumeric() {
		oType.setNumeric(STRING);
		assertEquals(STRING, oType.getNumeric());
	}

	@Test
	public void testGetShortName() {
		oType.setShortName(STRING);
		assertEquals(STRING, oType.getShortName());
	}

	@Test
	public void testGetVeryShortName() {
		oType.setVeryShortName(STRING);
		assertEquals(STRING, oType.getVeryShortName());
	}

	@Test
	public void testGetVerbose() {
		oType.setVerbose(STRING);
		assertEquals(STRING, oType.getVerbose());
	}

}
