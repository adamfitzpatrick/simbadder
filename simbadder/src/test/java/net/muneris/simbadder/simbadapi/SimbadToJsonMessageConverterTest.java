package net.muneris.simbadder.simbadapi;

import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_PARSE_ERROR;
import static net.muneris.simbadder.testutils.TestConstants.SIMBAD_RESPONSE_STRING;
import static net.muneris.simbadder.testutils.TestConstants.STRING;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertNotNull;

import net.muneris.simbadder.model.SimbadObject;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.mock.http.MockHttpInputMessage;

/**
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
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

    @Test
    public void testRead() throws IOException {
        message = new MockHttpInputMessage(SIMBAD_RESPONSE_STRING.getBytes());
        SimbadObject[] objects = converter.read(SimbadObject[].class, message);
        assertNotNull(objects);
    }

    @Test
    public void testReadWithJsonMappingException() throws IOException {
        exception.expect(JsonMappingException.class);
        message = new MockHttpInputMessage(SIMBAD_PARSE_ERROR.getBytes());
        converter.read(SimbadObject[].class, message);
    }

    @Test
    public void testSimbadToJsonMessageConverter() {
        assertNotNull(converter);
    }

}
