package net.muneris.simbadder.simbadapi;

import static net.muneris.simbadder.testUtils.TestConstants.SIMBAD_PARSE_ERROR;
import static net.muneris.simbadder.testUtils.TestConstants.SIMBAD_RESPONSE_STRING;
import static net.muneris.simbadder.testUtils.TestConstants.STRING;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.muneris.simbadder.exception.InputMessageNotReadableException;
import net.muneris.simbadder.exception.SimbadExceptionResponseHandler;
import net.muneris.simbadder.exception.SimbadParseException;
import net.muneris.simbadder.model.SimbadObject;
import net.muneris.simbadder.simbadapi.SimbadToJsonMessageConverter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.test.util.ReflectionTestUtils;

public class SimbadToJsonMessageConverterTest {

	SimbadToJsonMessageConverter converter;
	ObjectMapper mapper;
	MockHttpInputMessage message;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	@Before
	public void setUp() throws Exception {
		converter = new SimbadToJsonMessageConverter();
		mapper = createMock(ObjectMapper.class);
		message = new MockHttpInputMessage(STRING.getBytes());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimbadToJsonMessageConverter() {
		assertNotNull(converter);
	}

	@Test
	public void testRead() throws IOException {
		message = new MockHttpInputMessage(SIMBAD_RESPONSE_STRING.getBytes());
		SimbadObject[] objects = converter.read(SimbadObject[].class, message);
		assertNotNull(objects);
	}
	
	@Test
	public void testReadWithIOException() throws IOException {
		ReflectionTestUtils.setField(converter, "objectMapper", mapper);
		expect(mapper.readValue(String.format("[%s]", STRING), SimbadObject[].class)).andThrow(new IOException());
		replay(mapper);
		exception.expect(InputMessageNotReadableException.class);
		converter.read(SimbadObject[].class, message);
	}
	
	@Test
	public void testReadWithJsonParseException() throws IOException {
		ReflectionTestUtils.setField(converter, "objectMapper", mapper);
		expect(mapper.readValue(String.format("[%s]", STRING), SimbadObject[].class)).andThrow(new JsonParseException(null, null));
		replay(mapper);
		exception.expect(SimbadExceptionResponseHandler.class);
		converter.read(SimbadObject[].class, message);
	}
	
	@Test
	public void testReadWithJsonMappingException() throws IOException {
		exception.expect(SimbadParseException.class);
		message = new MockHttpInputMessage(SIMBAD_PARSE_ERROR.getBytes());
		converter.read(SimbadObject[].class, message);
	}

}
