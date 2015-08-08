package net.muneris.simbadder.client.simbadapi;

import static net.muneris.simbadder.client.testUtils.TestConstants.SIMBAD_RESPONSE_STRING;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.SimbadToJsonMessageConverter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;

public class SimbadToJsonMessageConverterTest {

	SimbadToJsonMessageConverter converter;
	ObjectMapper mapper = new ObjectMapper();
	MockHttpInputMessage message;
	
	@Before
	public void setUp() throws Exception {
		converter = new SimbadToJsonMessageConverter();
		message = new MockHttpInputMessage(SIMBAD_RESPONSE_STRING.getBytes());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimbadToJsonMessageConverter() {
		assertNotNull(converter);
	}

	@Test
	public void testReadInternalClassOfQextendsTHttpInputMessage() throws HttpMessageNotReadableException, IOException {
		SimbadObject[] objects = converter.read(SimbadObject[].class, message);
		assertNotNull(objects);
	}

}
