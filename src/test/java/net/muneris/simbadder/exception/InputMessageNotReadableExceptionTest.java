package net.muneris.simbadder.exception;

import static net.muneris.simbadder.testUtils.TestConstants.STRING;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InputMessageNotReadableExceptionTest {

	InputMessageNotReadableException exception = new InputMessageNotReadableException(new IOException(STRING));
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInputMessageNotReadableException() {
		assertNotNull(exception);
	}

}
