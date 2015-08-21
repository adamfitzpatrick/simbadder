package net.muneris.simbadder.simbadapi.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import net.muneris.simbadder.simbadapi.query.RadiusUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RadiusUnitTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParseString() {
		assertNull(RadiusUnit.parseString("foo"));
		assertEquals(RadiusUnit.DEGREES, RadiusUnit.parseString("d"));
		assertEquals(RadiusUnit.DEGREES, RadiusUnit.parseString("deg"));
		assertEquals(RadiusUnit.DEGREES, RadiusUnit.parseString("degs"));
		assertEquals(RadiusUnit.DEGREES, RadiusUnit.parseString("degree"));
		assertEquals(RadiusUnit.DEGREES, RadiusUnit.parseString("degrees"));
		assertEquals(RadiusUnit.MINUTES, RadiusUnit.parseString("m"));
		assertEquals(RadiusUnit.MINUTES, RadiusUnit.parseString("min"));
		assertEquals(RadiusUnit.MINUTES, RadiusUnit.parseString("mins"));
		assertEquals(RadiusUnit.MINUTES, RadiusUnit.parseString("minute"));
		assertEquals(RadiusUnit.MINUTES, RadiusUnit.parseString("minutes"));
		assertEquals(RadiusUnit.SECONDS, RadiusUnit.parseString("s"));
		assertEquals(RadiusUnit.SECONDS, RadiusUnit.parseString("sec"));
		assertEquals(RadiusUnit.SECONDS, RadiusUnit.parseString("secs"));
		assertEquals(RadiusUnit.SECONDS, RadiusUnit.parseString("second"));
		assertEquals(RadiusUnit.SECONDS, RadiusUnit.parseString("seconds"));
	}

	@Test
	public void testToString() {
		assertEquals("d",RadiusUnit.DEGREES.toString());
		assertEquals("m",RadiusUnit.MINUTES.toString());
		assertEquals("s",RadiusUnit.SECONDS.toString());
	}

}
