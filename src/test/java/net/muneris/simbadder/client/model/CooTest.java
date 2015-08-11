package net.muneris.simbadder.client.model;

import static net.muneris.simbadder.client.testUtils.TestConstants.*;
import static org.junit.Assert.*;
import net.muneris.simbadder.model.Coo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CooTest {

	Coo coo = new Coo();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCoo() {
		assertNotNull(coo);
	}

	@Test
	public void testGetWavelengthType() {
		coo.setWavelengthType(STRING);
		assertEquals(STRING, coo.getWavelengthType());
	}

}
