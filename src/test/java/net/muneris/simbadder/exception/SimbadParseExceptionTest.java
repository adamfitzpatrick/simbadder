package net.muneris.simbadder.exception;

import static net.muneris.simbadder.client.testUtils.TestConstants.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimbadParseExceptionTest {

	SimbadParseException exception = new SimbadParseException(STRING);
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimbadParseException() {
		assertNotNull(exception);
		assertEquals(STRING, exception.getMessage());
	}

	@Test
	public void testGetName() {
		assertEquals(SimbadException.PARSE_EXCEPTION, exception.getName());
	}

	@Test
	public void testGetFullName() {
		String fullName = SimbadException.EXCEPTION_PREFIX + SimbadException.PARSE_EXCEPTION;
		assertEquals(fullName, exception.getFullName());
	}

}
