package net.muneris.simbadder.exception;

import static net.muneris.simbadder.testUtils.TestConstants.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimbadFormattingExceptionTest {

	SimbadFormattingException exception = new SimbadFormattingException(STRING);
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimbadFormattingException() {
		assertNotNull(exception);
		String message = SimbadException.FORMATTING_EXCEPTION + ": " + STRING;
		assertEquals(message, exception.getMessage());
	}
	
	@Test
	public void testGetName() {
		assertEquals(SimbadException.FORMATTING_EXCEPTION, exception.getName());
	}

	@Test
	public void testGetFullName() {
		assertEquals(SimbadException.FORMATTING_EXCEPTION, exception.getFullName());
	}

}
